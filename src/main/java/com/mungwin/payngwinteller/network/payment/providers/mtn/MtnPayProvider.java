package com.mungwin.payngwinteller.network.payment.providers.mtn;

import com.mungwin.payngwinteller.network.payment.PayProvider;

public class MtnPayProvider implements PayProvider {
    @Override
    public boolean push() {
        return false;
    }

    @Override
    public void capture() {

    }
}
