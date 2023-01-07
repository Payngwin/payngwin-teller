package com.mungwin.payngwinteller.network.payment.dto.mtn;

public class MoMoPayResponse {
    private Double amount;
    private String currency;
    private Long financialTransactionId;
    private MoMoPayer payer;
    private String status;
    private String reason;
    private Boolean success;

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

    public Long getFinancialTransactionId() {
        return financialTransactionId;
    }

    public void setFinancialTransactionId(Long financialTransactionId) {
        this.financialTransactionId = financialTransactionId;
    }

    public MoMoPayer getPayer() {
        return payer;
    }

    public void setPayer(MoMoPayer payer) {
        this.payer = payer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "MoMoPayResponse{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", financialTransactionId=" + financialTransactionId +
                ", payer=" + payer +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", success=" + success +
                '}';
    }
}
