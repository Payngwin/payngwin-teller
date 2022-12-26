package com.mungwin.payngwinteller.network.payment;

public interface PayProvider {
    boolean push(
            String orderToken, String payerId, String returnEmail,
            String payerExpireDate, String payerCode, String comment);
    void capture();
}
