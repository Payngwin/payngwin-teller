package com.mungwin.payngwinteller.network.payment.dto.mtn;

public class MoMoPayRequest {
    private String amount;
    private String currency;
    private String externalId;
    private MoMoPayer payer;
    private String payerMessage;
    private String payeeNote;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public MoMoPayer getPayer() {
        return payer;
    }

    public void setPayer(MoMoPayer payer) {
        this.payer = payer;
    }

    public String getPayerMessage() {
        return payerMessage;
    }

    public void setPayerMessage(String payerMessage) {
        this.payerMessage = payerMessage;
    }

    public String getPayeeNote() {
        return payeeNote;
    }

    public void setPayeeNote(String payeeNote) {
        this.payeeNote = payeeNote;
    }
}
