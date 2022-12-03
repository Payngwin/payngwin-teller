package com.mungwin.payngwinteller.domain.model.account;

import com.mungwin.payngwinteller.domain.model.BaseSerialEntity;
import com.mungwin.payngwinteller.domain.model.BaseSerialEntityWithCreatorAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseSerialEntity {
    private String code;
    private String motive;
    private String status;
    private Double amount;
    @Column(name = "client_ip")
    private String clientIp;
    @Column(name = "debit_account_id")
    private Long debitAccountId;
    @Column(name = "credit_account_id")
    private Long creditAccountId;
    @Column(name = "collection_order_id")
    private Long collectionOrderId;
    @Column(name = "initiated_by")
    private String initiatedBy;
    @Column(name = "confirmed_by")
    private String confirmedBy;
    private UUID userId;
    private Instant createdAt;
    private Instant updatedAt;
    public static String generateCode() {
        return UUID.randomUUID().toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Long getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(Long debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public Long getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(Long creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public Long getCollectionOrderId() {
        return collectionOrderId;
    }

    public void setCollectionOrderId(Long collectionOrderId) {
        this.collectionOrderId = collectionOrderId;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
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
}
