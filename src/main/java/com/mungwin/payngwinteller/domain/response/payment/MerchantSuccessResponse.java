package com.mungwin.payngwinteller.domain.response.payment;

import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import com.mungwin.payngwinteller.domain.model.payment.PaymentTransaction;

public class MerchantSuccessResponse {
    private CollectionOrder order;
    private PaymentTransaction paymentTransaction;

    public CollectionOrder getOrder() {
        return order;
    }

    public void setOrder(CollectionOrder order) {
        this.order = order;
    }

    public PaymentTransaction getPaymentTransaction() {
        return paymentTransaction;
    }

    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }
}
