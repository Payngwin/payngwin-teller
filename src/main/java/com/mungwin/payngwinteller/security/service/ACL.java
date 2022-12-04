package com.mungwin.payngwinteller.security.service;


import com.mungwin.payngwinteller.security.Util;

public class ACL {
    public static final String[] USER = {"user.read", "user.update"};
    public static final String[] MERCHANT = Util.concatArray(USER, new String[]{"exam.*", "exam.create"});
    public static final String[] PRESIDENT = Util.concatArray(MERCHANT,
            new String[]{"qualification.*", "qualification.create", "qualification.read", "qualification.update"});
    public static final String[] ADMIN = Util.concatArray(PRESIDENT, new String[]{"user.*"});
}
