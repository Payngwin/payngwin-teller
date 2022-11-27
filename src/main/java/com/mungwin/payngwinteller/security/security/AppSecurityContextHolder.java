package com.mungwin.payngwinteller.security.security;


import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.iam.AppToken;
import com.mungwin.payngwinteller.domain.model.iam.Role;
import com.mungwin.payngwinteller.security.exception.ApiException;

public class AppSecurityContextHolder {
    private static final ThreadLocal<Principal> principal = new ThreadLocal<>();
    private static final ThreadLocal<AppToken>  token = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> failIfAbsent = ThreadLocal.withInitial(() -> true);

    public static Principal getPrincipal() {
        if (principal.get() == null && failIfAbsent.get()) {
            throw ApiException.USER_UNAUTHORIZED;
        }
        return principal.get();
    }
    public static void setPrincipal(Principal principal) {
        AppSecurityContextHolder.principal.set(principal);
    }

    public static AppToken getToken() {
        if (token.get() == null && failIfAbsent.get()) {
            throw ApiException.USER_UNAUTHORIZED;
        }
        return token.get();
    }

    public static void setToken(AppToken token) {
        AppSecurityContextHolder.token.set(token);
    }

    public static boolean shouldFailIfAbsent() {
        return failIfAbsent.get();
    }
    public static void setFailIfAbsent(boolean failIfAbsent) {
        AppSecurityContextHolder.failIfAbsent.set(failIfAbsent);
    }

    public static void clearPrincipal() {
        AppSecurityContextHolder.principal.remove();
        AppSecurityContextHolder.failIfAbsent.remove();
        AppSecurityContextHolder.token.remove();
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
