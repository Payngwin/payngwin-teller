package com.mungwin.payngwinteller.domain.model.account;

import com.mungwin.payngwinteller.domain.model.BaseSerialEntityWithCreatorAudit;
import com.mungwin.payngwinteller.domain.model.iam.utils.JsonBWrapper;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account extends BaseSerialEntityWithCreatorAudit {
    private Double currentBalance;
    private Double availableBalance;
    private Boolean isClosed = false;
    private Boolean isWithdrawalOpen = false;
    private String accountType;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonBWrapper<List<BillingAddress>> billingAddresses = new JsonBWrapper<>(new ArrayList<>());

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public JsonBWrapper<List<BillingAddress>> getBillingAddresses() {
        return billingAddresses;
    }

    public void setBillingAddresses(JsonBWrapper<List<BillingAddress>> billingAddresses) {
        this.billingAddresses = billingAddresses;
    }

    public Boolean getWithdrawalOpen() {
        return isWithdrawalOpen;
    }

    public void setWithdrawalOpen(Boolean withdrawalOpen) {
        isWithdrawalOpen = withdrawalOpen;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
