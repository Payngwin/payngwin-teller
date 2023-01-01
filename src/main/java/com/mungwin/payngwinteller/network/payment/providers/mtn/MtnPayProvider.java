package com.mungwin.payngwinteller.network.payment.providers.mtn;

import com.mungwin.payngwinteller.constant.PaymentProviderHolder;
import com.mungwin.payngwinteller.constant.TransactionStatuses;
import com.mungwin.payngwinteller.constant.UtilityAccount;
import com.mungwin.payngwinteller.domain.model.account.Account;
import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.iam.User;
import com.mungwin.payngwinteller.domain.model.iam.UserProfile;
import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import com.mungwin.payngwinteller.domain.model.payment.PaymentProvider;
import com.mungwin.payngwinteller.domain.model.payment.PaymentTransaction;
import com.mungwin.payngwinteller.domain.response.payment.MerchantSuccessResponse;
import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.exception.Precondition;
import com.mungwin.payngwinteller.network.payment.PayProvider;
import com.mungwin.payngwinteller.network.payment.clients.mtn.MoMoRestClient;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoPayRequest;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoPayResponse;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoPayer;
import com.mungwin.payngwinteller.network.payment.providers.BasePayProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class MtnPayProvider extends BasePayProvider implements PayProvider {
    private final MoMoRestClient moMoRestClient;

    public MtnPayProvider(MoMoRestClient moMoRestClient) {
        this.moMoRestClient = moMoRestClient;
    }

    @Override
    public boolean push(
            App app,
            String orderToken, String payerId,
            String returnEmail, String payerExpireDate,
            String payerCode, String comment) {
        Optional<CollectionOrder> orderOptional = collectionOrderRepository.findFirstByToken(orderToken);
        Optional<Account> merchantOptional = accountRepository.findById(app.getAccountId());
        Optional<User> userOptional = userRepository.findById(app.getUserId());
        Optional<UserProfile> profileOptional = userProfileRepository.findFirstByUserId(app.getUserId());

        Precondition.check(orderOptional.isPresent() && merchantOptional.isPresent() &&
                        userOptional.isPresent() && profileOptional.isPresent(),
                ApiException.RESOURCE_NOT_FOUND);

        CollectionOrder order = orderOptional.get();
        User user = userOptional.get();
        UserProfile profile = profileOptional.get();

        String accessToken = moMoRestClient.login().getAccess_token();

        String referenceId = UUID.randomUUID().toString();
        MoMoPayRequest moMoPayRequest = new MoMoPayRequest();
        moMoPayRequest.setAmount(String.valueOf(order.getAmount()));
        moMoPayRequest.setCurrency(order.getCurrency());
        moMoPayRequest.setExternalId(order.getExternalId());

        MoMoPayer payer = new MoMoPayer();
        payer.setPartyId(payerId);
        payer.setPartyIdType("MSISDN");
        moMoPayRequest.setPayer(payer);
        moMoPayRequest.setPayerMessage(order.getOComment());
        moMoPayRequest.setPayeeNote(comment);

        order.setProviderToken(referenceId);
        order.setNotifyEmail(returnEmail);
        order.setNotifyPhoneNumber(payerId);
        collectionOrderRepository.save(order);

        boolean pushed = moMoRestClient.requestToPay(moMoPayRequest, referenceId, accessToken);
        // start a thread to monitor this payment
        if (pushed) {
            Account merchant = merchantOptional.get();
            Account payngwin = findUtilityAccount(UtilityAccount.PAYNGWIN_NAME);
            Account paymentProvider = findUtilityAccount(UtilityAccount.PAYMENT_PROVIDER_NAME);
            Account collection = findUtilityAccount(UtilityAccount.COLLECTION_NAME);
            PaymentProvider mtn = PaymentProviderHolder.store.get(PaymentProviderHolder.MTN);
            (new Thread(() -> capture(
                    app, order, merchant, paymentProvider, payngwin, collection, mtn,
                    user.getEmail(), profile.getUsername()
            ))).start();
        }
        return pushed;
    }

    @Override
    public void capture(App app, CollectionOrder order, Account merchantAccount, Account providerAccount,
                        Account payngwinAccount, Account collectionAccount, PaymentProvider paymentProvider,
                        String merchantMail, String merchantUsername) {
        try {
            // slack 2 mins
            Thread.sleep(120000);
            MoMoPayResponse response = moMoRestClient.monitorForPayStatus(
                    order.getProviderToken(), moMoRestClient.login().getAccess_token(), 0);
            if (response.getSuccess()) {
                PaymentTransaction paymentTransaction = new PaymentTransaction();
                paymentTransaction.setStatus(response.getStatus());
                paymentTransaction.setAmount(response.getAmount());
                paymentTransaction.setAuthorizedBy(response.getPayer() != null ?
                        response.getPayer().getPartyId() : "");
                paymentTransaction.setStatus(response.getStatus());
                paymentTransaction.setPaymentTransactionId(String.valueOf(response.getFinancialTransactionId()));
                paymentTransaction.setOrderId(order.getId());
                paymentTransaction.setOrderTransactionToken(order.getProviderToken());
                paymentTransaction.setComment(response.getReason());
                paymentTransaction.setSenderAccount(response.getPayer() != null ?
                        response.getPayer().getPartyId() : "");
                paymentTransaction.setReceiverAccount(String.valueOf(app.getAccountId()));
                paymentTransaction.setCreatedAt(Instant.now());
                paymentTransaction.setUpdatedAt(Instant.now());
                paymentTransaction.setInitiatedBy(app.getId().toString());
                // update books
                MerchantSuccessResponse successResponse = updateBooks(
                        order, merchantAccount, providerAccount, payngwinAccount, collectionAccount,
                        paymentTransaction, paymentProvider);
                // forward to merchant site
                forwardToMerchantSite(successResponse, app);
                // notify success always copy system account, merchant account and payer
                sendSuccessEmail(successResponse, app, merchantMail, merchantUsername);
            } else {
                order.setStatus(TransactionStatuses.FAILED.toString());
                collectionOrderRepository.save(order);
                // notify failure always copy system account, merchant account and payer
                sendFailureEmail(order, app, paymentProvider.getName(), response.getReason(),
                        merchantMail, merchantUsername);
            }
        } catch (InterruptedException ignored) {}
    }
}
