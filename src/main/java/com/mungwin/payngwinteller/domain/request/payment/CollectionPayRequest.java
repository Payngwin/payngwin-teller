package com.mungwin.payngwinteller.domain.request.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CollectionPayRequest {
    @NotBlank(message = "channel is required")
    private String channel;
    private String comment;
    @NotNull(message = "returnEmail is required")
    private String returnEmail;
    private String amount;
    @NotBlank(message = "payerId is required")
    private String payerId;
    private String payerCode;
    private String payerExpireDate;
    @NotBlank(message = "token is required")
    private String orderToken;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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

    public String getPayerCode() {
        return payerCode;
    }

    public void setPayerCode(String payerCode) {
        this.payerCode = payerCode;
    }

    public String getPayerExpireDate() {
        return payerExpireDate;
    }

    public void setPayerExpireDate(String payerExpireDate) {
        this.payerExpireDate = payerExpireDate;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }
}
