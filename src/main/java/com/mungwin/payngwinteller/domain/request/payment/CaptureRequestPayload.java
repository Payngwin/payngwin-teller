package com.mungwin.payngwinteller.domain.request.payment;


import javax.validation.constraints.NotBlank;

public class CaptureRequestPayload {
    private String returnEmail;
    @NotBlank(message = "amount is required")
    private String amount;
    private String payerPhoneNumber;

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

    public String getPayerPhoneNumber() {
        return payerPhoneNumber;
    }

    public void setPayerPhoneNumber(String payerPhoneNumber) {
        this.payerPhoneNumber = payerPhoneNumber;
    }
}
