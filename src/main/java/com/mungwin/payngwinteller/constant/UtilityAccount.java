package com.mungwin.payngwinteller.constant;

public class UtilityAccount {
    public static Long COLLECTION_ID;
    public static final String COLLECTION_NAME = "COLLECTION";
    public static Long PAYMENT_PROVIDER_ID;
    public static final String PAYMENT_PROVIDER_NAME = "PAYMENT_PROVIDER";
    public static Long PAYNGWIN_ID;
    public static final String PAYNGWIN_NAME = "PAYNGWIN";

    public static void initialize(Long collection, Long provider, Long payngwin) {
        COLLECTION_ID = collection;
        PAYMENT_PROVIDER_ID = provider;
        PAYNGWIN_ID = payngwin;
    }
}
