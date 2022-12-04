package com.mungwin.payngwinteller.security.service;


import com.mungwin.payngwinteller.security.Util;

public class ACL {
    public static final String[] APP = {"payment.order.create", "payment.order.read",
            "payment.order.update", "payment.order.close"};
    public static final String[] MERCHANT = Util.concatArray(APP, new String[]{"exam.*", "exam.create"});
    public static final String[] PRESIDENT = Util.concatArray(MERCHANT,
            new String[]{"qualification.*", "qualification.create", "qualification.read", "qualification.update"});
    public static final String[] ADMIN = Util.concatArray(PRESIDENT, new String[]{"user.*"});
}
