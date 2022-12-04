package com.mungwin.payngwinteller.domain.model.payment;


import com.mungwin.payngwinteller.domain.model.BaseSerialEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "collection_order")
public class CollectionOrder extends BaseSerialEntity {
    private String token;
    private Double amount;
    private String currency;
    private String status;
    private String tag;
    @Column(name = "external_id")
    private String externalId;
    @Column(name = "return_url")
    private String returnUrl;
    @Column(name = "return_token")
    private String returnToken;
    @Column(name = "cancel_url")
    private String cancelUrl;
    @Column(name = "logo_url")
    private String orderLogoUrl;
    @Column(name = "line_items")
    private String lineItems;
    @Column(name = "pay_request")
    private String payRequest;
    @Column(name = "o_comment")
    private String oComment;
    @Column(name = "o_source")
    private String oSource;
    @Column(name = "provider_token")
    private String providerToken;
    @Column(name = "provider_transaction_id")
    private String providerTransactionId;
    @Column(name = "payment_channel")
    private String paymentChannel;
    @Column(name = "callback_response_body")
    private String callbackResponseBody;
    @Column(name = "callback_response_status_code")
    private Integer callbackResponseStatusCode;
    @Column(name = "notify_email")
    private String notifyEmail;
    @Column(name = "notify_phone_number")
    private String notifyPhoneNumber;
    @Column(name = "capture_mode")
    private Boolean captureMode;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    @Column(name = "account_id")
    private Long accountId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getReturnToken() {
        return returnToken;
    }

    public void setReturnToken(String returnToken) {
        this.returnToken = returnToken;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getOrderLogoUrl() {
        return orderLogoUrl;
    }

    public void setOrderLogoUrl(String orderLogoUrl) {
        this.orderLogoUrl = orderLogoUrl;
    }

    public String getLineItems() {
        return lineItems;
    }

    public void setLineItems(String lineItems) {
        this.lineItems = lineItems;
    }

    public String getPayRequest() {
        return payRequest;
    }

    public void setPayRequest(String payRequest) {
        this.payRequest = payRequest;
    }

    public String getOComment() {
        return oComment;
    }

    public void setOComment(String oComment) {
        this.oComment = oComment;
    }

    public String getOSource() {
        return oSource;
    }

    public void setOSource(String oSource) {
        this.oSource = oSource;
    }

    public String getProviderToken() {
        return providerToken;
    }

    public void setProviderToken(String providerToken) {
        this.providerToken = providerToken;
    }

    public String getProviderTransactionId() {
        return providerTransactionId;
    }

    public void setProviderTransactionId(String providerTransactionId) {
        this.providerTransactionId = providerTransactionId;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getCallbackResponseBody() {
        return callbackResponseBody;
    }

    public void setCallbackResponseBody(String callbackResponseBody) {
        this.callbackResponseBody = callbackResponseBody;
    }

    public Integer getCallbackResponseStatusCode() {
        return callbackResponseStatusCode;
    }

    public void setCallbackResponseStatusCode(Integer callbackResponseStatusCode) {
        this.callbackResponseStatusCode = callbackResponseStatusCode;
    }

    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public String getNotifyPhoneNumber() {
        return notifyPhoneNumber;
    }

    public void setNotifyPhoneNumber(String notifyPhoneNumber) {
        this.notifyPhoneNumber = notifyPhoneNumber;
    }

    public Boolean getCaptureMode() {
        return captureMode;
    }

    public void setCaptureMode(Boolean captureMode) {
        this.captureMode = captureMode;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
