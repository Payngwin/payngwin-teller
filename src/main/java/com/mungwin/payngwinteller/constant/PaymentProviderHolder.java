package com.mungwin.payngwinteller.constant;

import com.mungwin.payngwinteller.domain.model.payment.PaymentProvider;

import java.util.HashMap;
import java.util.Map;

public class PaymentProviderHolder {
    public static final String MTN = "MTN";
    public static final String ORANGE = "ORANGE";
    public static final Map<String, PaymentProvider> store = new HashMap<>();
    public static void initialize(PaymentProvider... p) {
        store.put(MTN, p[0]);
        store.put(ORANGE, p[1]);
    }
}
