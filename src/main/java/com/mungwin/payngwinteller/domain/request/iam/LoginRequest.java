package com.mungwin.payngwinteller.domain.request.iam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private Long durationInSeconds;

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

    public Long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }
}
