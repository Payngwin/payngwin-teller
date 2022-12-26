package com.mungwin.payngwinteller.network.payment.providers;

import com.mungwin.payngwinteller.constant.PaymentCharges;
import com.mungwin.payngwinteller.constant.TransactionStatuses;
import com.mungwin.payngwinteller.domain.model.account.Account;
import com.mungwin.payngwinteller.domain.model.account.AccountBalanceHistory;
import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import com.mungwin.payngwinteller.domain.model.payment.PaymentProvider;
import com.mungwin.payngwinteller.domain.model.payment.PaymentTransaction;
import com.mungwin.payngwinteller.domain.repository.account.AccountBalanceHistoryRepository;
import com.mungwin.payngwinteller.domain.repository.account.AccountRepository;
import com.mungwin.payngwinteller.domain.repository.payment.CollectionOrderRepository;
import com.mungwin.payngwinteller.domain.repository.payment.PaymentTransactionRepository;
import com.mungwin.payngwinteller.domain.response.payment.MerchantSuccessResponse;
import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.exception.Precondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@Component
public class BasePayProvider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate;
    protected CollectionOrderRepository collectionOrderRepository;
    protected AccountRepository accountRepository;
    protected AccountBalanceHistoryRepository accountBalanceHistoryRepository;
    protected PaymentTransactionRepository paymentTransactionRepository;

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
        Double payngwinCredit = PaymentCharges.MOBILE.getCharge() * amount;
        Double providerCredit = paymentProvider.getCharge() * amount;
        Double merchantCredit = amount - payngwinCredit - providerCredit;

        // account balance history opening balances
        AccountBalanceHistory merchantBalanceHistory = new AccountBalanceHistory();
        merchantBalanceHistory.setOpeningBalance(merchantAccount.getCurrentBalance());

        AccountBalanceHistory payngwinBalanceHistory = new AccountBalanceHistory();
        payngwinBalanceHistory.setOpeningBalance(payngwinAccount.getCurrentBalance());

        AccountBalanceHistory providerBalanceHistory = new AccountBalanceHistory();
        providerBalanceHistory.setOpeningBalance(providerAccount.getCurrentBalance());

        AccountBalanceHistory collectionBalanceHistory = new AccountBalanceHistory();
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
        merchantBalanceHistory.setAccountId(merchantAccount.getId());
        merchantBalanceHistory.setClosingBalance(merchantAccount.getCurrentBalance());
        merchantBalanceHistory.setMovement(merchantCredit);

        payngwinBalanceHistory.setAccountId(payngwinAccount.getId());
        payngwinBalanceHistory.setClosingBalance(payngwinAccount.getCurrentBalance());
        payngwinBalanceHistory.setMovement(payngwinCredit);

        providerBalanceHistory.setAccountId(providerAccount.getId());
        providerBalanceHistory.setClosingBalance(providerAccount.getCurrentBalance());
        providerBalanceHistory.setMovement(providerCredit);

        collectionBalanceHistory.setAccountId(collectionAccount.getId());
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
}
