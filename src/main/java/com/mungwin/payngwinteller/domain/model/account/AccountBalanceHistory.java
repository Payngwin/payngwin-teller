package com.mungwin.payngwinteller.domain.model.account;



import com.mungwin.payngwinteller.domain.model.BaseSerialEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "account_balance_history")
public class AccountBalanceHistory extends BaseSerialEntity {
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "opening_balance")
    private Double openingBalance;
    private Double movement;
    @Column(name = "closing_balance")
    private Double closingBalance;
    private Boolean direction;
    @Column(name = "changed_at")
    private Instant changedAt = Instant.now();
    private String comment;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Double getMovement() {
        return movement;
    }

    public void setMovement(Double movement) {
        this.movement = movement;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Boolean getDirection() {
        return direction;
    }

    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Instant changedAt) {
        this.changedAt = changedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
