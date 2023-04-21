package com.mungwin.payngwinteller.domain.model.iam;


import com.mungwin.payngwinteller.domain.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "app_token")
public class AppToken extends BaseEntity {
    private Instant createdAt = Instant.now();
    private String  appId;
    private String token;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
