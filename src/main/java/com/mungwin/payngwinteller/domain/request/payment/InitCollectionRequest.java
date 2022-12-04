package com.mungwin.payngwinteller.domain.request.payment;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class InitCollectionRequest {
    @NotNull(message = "amount is required")
    private Double amount;
    @NotBlank(message = "currency is required")
    private String currency;
    @NotBlank(message = "externalId is required")
    private String externalId;
    @NotBlank(message = "returnUrl is required")
    private String returnUrl;
    @NotBlank(message = "returnToken is required for security validation")
    private String returnToken;
    @NotBlank(message = "cancelUrl is required")
    private String cancelUrl;
    private String orderLogoUrl;
    private List<Object> lineItems;
    private Boolean captureMode = true;
    private String comment;
    private String source;
    private String tag;
    public String lineItemsToString() {
        try {
            return new ObjectMapper().writeValueAsString(this.lineItems);
        } catch (Exception ignored) {
            return  null;
        }
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

    public List<Object> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<Object> lineItems) {
        this.lineItems = lineItems;
    }

    public Boolean getCaptureMode() {
        return captureMode;
    }

    public void setCaptureMode(Boolean captureMode) {
        this.captureMode = captureMode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
