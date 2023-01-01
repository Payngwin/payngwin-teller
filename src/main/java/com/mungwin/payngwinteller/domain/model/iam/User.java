package com.mungwin.payngwinteller.domain.model.iam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mungwin.payngwinteller.domain.model.BaseEntityWithBasicAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"version","password"})
public class User extends BaseEntityWithBasicAudit {
    private String email;
    private String password;
    private UUID roleId;
    @Column(name = "email_verified_at")
    private Instant emailVerifiedAt;

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

    public Instant getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Instant emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }
}
