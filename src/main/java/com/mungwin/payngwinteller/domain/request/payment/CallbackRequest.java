package com.mungwin.payngwinteller.domain.request.payment;

public class CallbackRequest {
    private Double amount;
    private String currency;
    private String comment;
    private String status;
    private String externalId;
    private String paymentTransactionId;
    private String orderTransactionToken;
    private String returnToken;
    private String notifyEmail;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public String getOrderTransactionToken() {
        return orderTransactionToken;
    }

    public void setOrderTransactionToken(String orderTransactionToken) {
        this.orderTransactionToken = orderTransactionToken;
    }

    public String getReturnToken() {
        return returnToken;
    }

    public void setReturnToken(String returnToken) {
        this.returnToken = returnToken;
    }

    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }
}
