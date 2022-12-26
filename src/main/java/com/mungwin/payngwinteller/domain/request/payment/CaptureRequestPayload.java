package com.mungwin.payngwinteller.domain.request.payment;


public class CaptureRequestPayload {
    private String returnEmail;
    private String amount;
    private String payerId;
    private String payerExpireDate;
    private String payerCode;
    private String comment;

    public String getReturnEmail() {
        return returnEmail;
    }

    public void setReturnEmail(String returnEmail) {
        this.returnEmail = returnEmail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerExpireDate() {
        return payerExpireDate;
    }

    public void setPayerExpireDate(String payerExpireDate) {
        this.payerExpireDate = payerExpireDate;
    }

    public String getPayerCode() {
        return payerCode;
    }

    public void setPayerCode(String payerCode) {
        this.payerCode = payerCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
