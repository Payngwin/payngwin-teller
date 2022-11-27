package com.mungwin.payngwinteller.domain.response.iam;


import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.iam.Role;

public class AuthResponse {
    private App app;
    private Role role;
    private JWTResponse jwt;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public JWTResponse getJwt() {
        return jwt;
    }

    public void setJwt(JWTResponse jwt) {
        this.jwt = jwt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
