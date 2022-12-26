package com.mungwin.payngwinteller.constant;

public enum PaymentCharges {
    MOBILE(0.01),
    CARD(0.02)
    ;
    private double charge;

    PaymentCharges(double charge) {
        this.charge = charge;
    }

    public double getCharge() {
        return charge;
    }
}
