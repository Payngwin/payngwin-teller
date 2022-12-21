package com.mungwin.payngwinteller.domain.model.account;

import com.mungwin.payngwinteller.domain.model.BaseSerialEntity;
import com.mungwin.payngwinteller.domain.model.iam.utils.JsonBWrapper;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Account extends BaseSerialEntity {
    private Double currentBalance;
    private Double availableBalance;
    private Boolean isClosed = false;
    private Boolean isWithdrawalOpen = false;
    private String accountType;
    private UUID userId;
    private Instant createdAt;
    private Instant updatedAt;
    private String name;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
