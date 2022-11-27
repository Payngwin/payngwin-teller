package com.mungwin.payngwinteller.domain.model.account;

import java.util.Objects;

public class BillingAddress {
    private String scope;
    private String email;
    private String name;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillingAddress that = (BillingAddress) o;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
    public static class AddressScope {
        public static final String BLIND_COPY = "bcc";
        public static final String COPY = "cc";
        public static final String MAIN = "main";
    }
}
