package com.mungwin.payngwinteller.domain.model.account;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountType {
    public static final String CHECKING = "account.checking";
    public static final String SAVINGS = "account.savings";
    public static final String CERTIFICATE_OF_DEPOSIT = "account.certificate_of_deposit";
    @Id
    private String id;
    private Boolean isPayingInterest;
    private Double minimumRequiredBalance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getPayingInterest() {
        return isPayingInterest;
    }

    public void setPayingInterest(Boolean payingInterest) {
        isPayingInterest = payingInterest;
    }

    public Double getMinimumRequiredBalance() {
        return minimumRequiredBalance;
    }

    public void setMinimumRequiredBalance(Double minimumRequiredBalance) {
        this.minimumRequiredBalance = minimumRequiredBalance;
    }
}
