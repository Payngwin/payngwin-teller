package com.mungwin.payngwinteller.network.payment.dto.mtn;


import com.mungwin.payngwinteller.domain.request.payment.CaptureRequestPayload;

import javax.validation.constraints.NotBlank;

public class MoMoUserPayRequest extends CaptureRequestPayload {
    @NotBlank(message = "payerPhoneNumber is required")
    private String payerPhoneNumber;

    @Override
    public String getPayerPhoneNumber() {
        return payerPhoneNumber;
    }

    @Override
    public void setPayerPhoneNumber(String payerPhoneNumber) {
        this.payerPhoneNumber = payerPhoneNumber;
    }
}
