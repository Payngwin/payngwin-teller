package com.mungwin.payngwinteller.network.payment;

import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoPayer;
import com.mungwin.payngwinteller.network.payment.providers.mtn.MtnPayProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayProviderFactory {
    private MtnPayProvider mtnPayProvider;

    @Autowired
    public void setMtnPayProvider(MtnPayProvider mtnPayProvider) {
        this.mtnPayProvider = mtnPayProvider;
    }
    public PayProvider getPayProvider(String channel) {
        switch (channel) {
            case "mtn":
            case "MTN":
            case "momo":
            case "MOMO":
                return mtnPayProvider;
            default:
                throw ApiException.RESOURCE_NOT_FOUND;
        }
    }
}
