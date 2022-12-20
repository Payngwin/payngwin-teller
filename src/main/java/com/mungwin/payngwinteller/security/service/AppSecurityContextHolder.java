package com.mungwin.payngwinteller.security.service;


import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.iam.AppToken;
import com.mungwin.payngwinteller.domain.model.iam.Role;
import com.mungwin.payngwinteller.exception.ApiException;

public class AppSecurityContextHolder {
    private static final ThreadLocal<Principal> principal = new ThreadLocal<>();
    private static final ThreadLocal<AppToken>  token = new ThreadLocal<>();
    private static final ThreadLocal<String> consoleId = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> failIfAbsent = ThreadLocal.withInitial(() -> true);

    public static Principal getPrincipal() {
        if (principal.get() == null && failIfAbsent.get()) {
            throw ApiException.APP_UNAUTHORIZED;
        }
        return principal.get();
    }
    public static void setPrincipal(Principal principal) {
        AppSecurityContextHolder.principal.set(principal);
    }

    public static AppToken getToken() {
        if (token.get() == null && failIfAbsent.get()) {
            throw ApiException.APP_UNAUTHORIZED;
        }
        return token.get();
    }

    public static void setToken(AppToken token) {
        AppSecurityContextHolder.token.set(token);
    }

    public static String getConsoleId() {
        if (consoleId.get() == null) throw ApiException.INVALID_RESOURCE_ID;
        return consoleId.get();
    }

    public static void setConsoleId(String consoleId) {
        AppSecurityContextHolder.consoleId.set(consoleId);
    }

    public static boolean shouldFailIfAbsent() {
        return failIfAbsent.get();
    }
    public static void setFailIfAbsent(boolean failIfAbsent) {
        AppSecurityContextHolder.failIfAbsent.set(failIfAbsent);
    }

    public static void flushContext() {
        AppSecurityContextHolder.principal.remove();
        AppSecurityContextHolder.failIfAbsent.remove();
        AppSecurityContextHolder.token.remove();
        AppSecurityContextHolder.consoleId.remove();
    }
    public static class Principal {
        private App user;
        private Role role;

        public Principal(App user, Role role) {
            this.user = user;
            this.role = role;
        }

        public App getUser() {
            return user;
        }

        public void setUser(App user) {
            this.user = user;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }
    }
}
