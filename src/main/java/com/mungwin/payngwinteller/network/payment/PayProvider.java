package com.mungwin.payngwinteller.network.payment;

import com.mungwin.payngwinteller.domain.model.account.Account;
import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import com.mungwin.payngwinteller.domain.model.payment.PaymentProvider;

public interface PayProvider {
    boolean push(
            App app, String orderToken, String payerId, String returnEmail,
            String payerExpireDate, String payerCode, String comment);
    void capture(
            App app, CollectionOrder order, Account merchantAccount, Account providerAccount,
            Account payngwinAccount, Account collectionAccount, PaymentProvider paymentProvider,
            String merchantMail, String merchantUsername);
}
