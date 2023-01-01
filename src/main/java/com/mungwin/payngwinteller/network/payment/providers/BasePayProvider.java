package com.mungwin.payngwinteller.network.payment.providers;

import com.mungwin.payngwinteller.constant.*;
import com.mungwin.payngwinteller.domain.model.account.Account;
import com.mungwin.payngwinteller.domain.model.account.AccountBalanceHistory;
import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.iam.User;
import com.mungwin.payngwinteller.domain.model.iam.UserProfile;
import com.mungwin.payngwinteller.domain.model.mail.EmailAddress;
import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import com.mungwin.payngwinteller.domain.model.payment.PaymentProvider;
import com.mungwin.payngwinteller.domain.model.payment.PaymentTransaction;
import com.mungwin.payngwinteller.domain.repository.account.AccountBalanceHistoryRepository;
import com.mungwin.payngwinteller.domain.repository.account.AccountRepository;
import com.mungwin.payngwinteller.domain.repository.iam.UserProfileRepository;
import com.mungwin.payngwinteller.domain.repository.iam.UserRepository;
import com.mungwin.payngwinteller.domain.repository.payment.CollectionOrderRepository;
import com.mungwin.payngwinteller.domain.repository.payment.PaymentTransactionRepository;
import com.mungwin.payngwinteller.domain.response.payment.MerchantSuccessResponse;
import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.exception.Precondition;
import com.mungwin.payngwinteller.i18n.I18nContext;
import com.mungwin.payngwinteller.mail.EmailService;
import com.mungwin.payngwinteller.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static com.mungwin.payngwinteller.i18n.I18nService.t;

@Component
public class BasePayProvider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate;
    protected CollectionOrderRepository collectionOrderRepository;
    protected AccountRepository accountRepository;
    protected AccountBalanceHistoryRepository accountBalanceHistoryRepository;
    protected PaymentTransactionRepository paymentTransactionRepository;
    protected UserRepository userRepository;
    protected UserProfileRepository userProfileRepository;
    protected EmailService emailService;

    @Autowired
    public void setRestTemplate(@Qualifier("callbackRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCollectionOrderRepository(CollectionOrderRepository collectionOrderRepository) {
        this.collectionOrderRepository = collectionOrderRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setAccountBalanceHistoryRepository(AccountBalanceHistoryRepository accountBalanceHistoryRepository) {
        this.accountBalanceHistoryRepository = accountBalanceHistoryRepository;
    }

    @Autowired
    public void setPaymentTransactionRepository(PaymentTransactionRepository paymentTransactionRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Transactional
    public MerchantSuccessResponse updateBooks(
            CollectionOrder order,
            Account merchantAccount,
            Account providerAccount,
            Account payngwinAccount,
            Account collectionAccount,
            PaymentTransaction paymentTransaction,
            PaymentProvider paymentProvider) {
        /*
            This is posting the transaction
            2. The teller credits the merchant account
            3. The teller creates an account balance history record
            4. The teller credits the provider account
            5. The teller creates an account balance history record
            6. The teller credits payngwin account
            7. The teller creates an account balance history record
            8. The teller debits the collection account
            9. The teller creates an account balance history record
            10. The teller registers a payment transaction record
         */
        Double amount = paymentTransaction.getAmount();
        Double payngwinCredit = getPayngwinCharge(paymentProvider.getName()) * amount;
        Double providerCredit = paymentProvider.getCharge() * amount;
        Double merchantCredit = amount - payngwinCredit - providerCredit;

        // account balance history opening balances
        AccountBalanceHistory merchantBalanceHistory = new AccountBalanceHistory();
        merchantBalanceHistory.setAccountId(merchantAccount.getId());
        merchantBalanceHistory.setOpeningBalance(merchantAccount.getCurrentBalance());

        AccountBalanceHistory payngwinBalanceHistory = new AccountBalanceHistory();
        payngwinBalanceHistory.setAccountId(payngwinAccount.getId());
        payngwinBalanceHistory.setOpeningBalance(payngwinAccount.getCurrentBalance());

        AccountBalanceHistory providerBalanceHistory = new AccountBalanceHistory();
        providerBalanceHistory.setAccountId(providerAccount.getId());
        providerBalanceHistory.setOpeningBalance(providerAccount.getCurrentBalance());

        AccountBalanceHistory collectionBalanceHistory = new AccountBalanceHistory();
        collectionBalanceHistory.setAccountId(collectionAccount.getId());
        collectionBalanceHistory.setOpeningBalance(collectionAccount.getCurrentBalance());

        // credit accounts
        merchantAccount.setAvailableBalance(merchantAccount.getAvailableBalance() + merchantCredit);
        merchantAccount.setCurrentBalance(merchantAccount.getCurrentBalance() + merchantCredit);

        providerAccount.setCurrentBalance(providerAccount.getCurrentBalance() + providerCredit);
        payngwinAccount.setCurrentBalance(payngwinAccount.getCurrentBalance() + payngwinCredit);
        collectionAccount.setCurrentBalance(collectionAccount.getCurrentBalance() + amount);

        accountRepository.saveAll(Arrays.asList(
                merchantAccount, providerAccount, payngwinAccount, collectionAccount
        ));

        paymentTransaction.setUpdatedAt(Instant.now());
        paymentTransactionRepository.save(paymentTransaction);
        // balance history closing balances
        merchantBalanceHistory.setClosingBalance(merchantAccount.getCurrentBalance());
        merchantBalanceHistory.setMovement(merchantCredit);

        payngwinBalanceHistory.setClosingBalance(payngwinAccount.getCurrentBalance());
        payngwinBalanceHistory.setMovement(payngwinCredit);

        providerBalanceHistory.setClosingBalance(providerAccount.getCurrentBalance());
        providerBalanceHistory.setMovement(providerCredit);

        collectionBalanceHistory.setClosingBalance(collectionAccount.getCurrentBalance());
        collectionBalanceHistory.setMovement(amount);

        for (AccountBalanceHistory history: new AccountBalanceHistory[]{
                merchantBalanceHistory, payngwinBalanceHistory,
                providerBalanceHistory, collectionBalanceHistory}) {
            history.setDirection(true);
            history.setComment(order.getOComment());
            history.setChangedAt(Instant.now());
            history.setPaymentTransactionId(paymentTransaction.getId());
        }

        accountBalanceHistoryRepository.saveAll(Arrays.asList(
                merchantBalanceHistory, payngwinBalanceHistory, providerBalanceHistory, collectionBalanceHistory
        ));

        order.setStatus(TransactionStatuses.POSTED.toString());
        collectionOrderRepository.save(order);

        MerchantSuccessResponse successResponse = new MerchantSuccessResponse();
        successResponse.setOrder(order);
        successResponse.setPaymentTransaction(paymentTransaction);
        return successResponse;
    }
    public void forwardToMerchantSite(MerchantSuccessResponse response, App app) {
        CollectionOrder order = response.getOrder();
        try {
            ResponseEntity<String> entity = restTemplate.postForEntity(
                    app.getCallbackUrl(), response, String.class
            );
            order.setCallbackResponseBody(entity.getBody());
            order.setCallbackResponseStatusCode(entity.getStatusCodeValue());
        } catch (HttpClientErrorException ex) {
            order.setCallbackResponseStatusCode(ex.getRawStatusCode());
            order.setCallbackResponseBody(ex.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        collectionOrderRepository.save(order);
    }
    public void sendSuccessEmail(MerchantSuccessResponse response, App app, String merchantMail, String merchantUsername) {
        CollectionOrder order = response.getOrder();
        PaymentTransaction transaction = response.getPaymentTransaction();
        Context ctx = new Context();
        ctx.setVariable("merchantName", app.getName());
        ctx.setVariable("merchantLogoUrl", app.getLogoUrl());
        ctx.setVariable("title", t("payment_success_notice"));
        ctx.setVariable("greeting", String.format("%s, %s", t("hello"), order.getNotifyEmail()));
        ctx.setVariable("message", t("payment_was_made"));
        ctx.setVariable("heading", t("transaction_details"));
        ctx.setVariable("txtDate", t("date"));
        ctx.setVariable("paymentDate", LocalDateTime
                .from(transaction.getCreatedAt())
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", I18nContext.getLang())));
        ctx.setVariable("txtAmount", t("amount"));
        ctx.setVariable("amount", Util.decimalFormat()
                .format(transaction.getAmount()));
        ctx.setVariable("txtCurrency", t("currency"));
        ctx.setVariable("currency", order.getCurrency());
        ctx.setVariable("txtTransactionId", t("transaction_id"));
        ctx.setVariable("transactionId", transaction.getPaymentTransactionId());
        ctx.setVariable("txtChannel", t("payment_channel"));
        ctx.setVariable("channel", transaction.getPaymentChannel());
        emailService.sendStub("payment-success-notification", ctx, t("payment_success_notice"),
                Addresses.mailFrom, new EmailAddress(order.getNotifyEmail()), null,
                Arrays.asList(
                        new EmailAddress(merchantMail, merchantUsername),
                        Addresses.copyDelivery
                ));

    }
    public void sendFailureEmail(CollectionOrder order, App app, String paymentChannel, String reason,
                                 String merchantMail, String merchantUsername) {
        Context ctx = new Context();
        ctx.setVariable("merchantName", app.getName());
        ctx.setVariable("merchantLogoUrl", app.getLogoUrl());
        ctx.setVariable("title", t("payment_failure_notice"));
        ctx.setVariable("greeting", String.format("%s, %s", t("hello"), order.getNotifyEmail()));
        ctx.setVariable("message", t("payment_failed"));
        ctx.setVariable("heading", t("transaction_details"));
        ctx.setVariable("txtDate", t("date"));
        ctx.setVariable("paymentDate", LocalDateTime
                .from(order.getUpdatedAt())
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", I18nContext.getLang())));
        ctx.setVariable("txtAmount", t("amount"));
        ctx.setVariable("amount", Util.decimalFormat()
                .format(order.getAmount()));
        ctx.setVariable("txtCurrency", t("currency"));
        ctx.setVariable("currency", order.getCurrency());
        ctx.setVariable("txtChannel", t("payment_channel"));
        ctx.setVariable("channel", paymentChannel);
        ctx.setVariable("txtReason", t("reason"));
        ctx.setVariable("reason", reason);
        emailService.sendStub("payment-failure-notification", ctx, t("payment_success_notice"),
                Addresses.mailFrom, new EmailAddress(order.getNotifyEmail()), null,
                Arrays.asList(
                        new EmailAddress(merchantMail, merchantUsername),
                        Addresses.copyDelivery
                ));

    }
    public Account findUtilityAccount(String name) {
        switch (name) {
            case UtilityAccount.COLLECTION_NAME: {
                Optional<Account> optional = accountRepository.findById(UtilityAccount.COLLECTION_ID);
                Precondition.check(optional.isPresent(), ApiException.RESOURCE_NOT_FOUND);
                return optional.get();
            }
            case UtilityAccount.PAYNGWIN_NAME: {
                Optional<Account> optional = accountRepository.findById(UtilityAccount.PAYNGWIN_ID);
                Precondition.check(optional.isPresent(), ApiException.RESOURCE_NOT_FOUND);
                return optional.get();
            }
            case UtilityAccount.PAYMENT_PROVIDER_NAME: {
                Optional<Account> optional = accountRepository.findById(UtilityAccount.PAYMENT_PROVIDER_ID);
                Precondition.check(optional.isPresent(), ApiException.RESOURCE_NOT_FOUND);
                return optional.get();
            }
            default:
                throw ApiException.RESOURCE_NOT_FOUND;
        }
    }

    double getPayngwinCharge(String name) {
        switch (name) {
            case PaymentProviderHolder.MTN:
            case PaymentProviderHolder.ORANGE:
                return PaymentCharges.MOBILE.getCharge();
            default:
                return PaymentCharges.CARD.getCharge();
        }
    }
}
