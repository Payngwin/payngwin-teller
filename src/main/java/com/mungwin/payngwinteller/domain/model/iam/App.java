package com.mungwin.payngwinteller.domain.model.iam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mungwin.payngwinteller.domain.model.BaseEntityWithCreatorAudit;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"password"})
public class App extends BaseEntityWithCreatorAudit {
    private String name;
    private String logoUrl;
    private String callbackUrl;
    private String email;
    private String password;
    private UUID roleId;
    private Long accountId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
