package com.mungwin.payngwinteller.security.service.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreApprove {
    String[] value() default {""};
    Class<? extends HandlesApproval> handler() default DefaultApproveHandler.class;
}
