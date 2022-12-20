package com.mungwin.payngwinteller.network.payment;

public interface PayProvider {
    boolean push();
    void capture();
}
