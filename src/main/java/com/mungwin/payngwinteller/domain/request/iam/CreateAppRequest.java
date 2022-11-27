package com.mungwin.payngwinteller.domain.request.iam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateAppRequest {
    @NotBlank
    private String name;
    private String password;
    @NotNull
    private Long accountId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
