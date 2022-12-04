package com.mungwin.payngwinteller.security.service;



import com.mungwin.payngwinteller.domain.model.iam.Role;

import java.util.UUID;

public class AppDefaults {
    private static Role defaultUserRole;

    public static UUID getDefaultUserRoleId() {
        return defaultUserRole.getId();
    }

    public static Role getDefaultUserRole() {
        return defaultUserRole;
    }

    public static void setDefaultUserRole(Role defaultUserRole) {
        AppDefaults.defaultUserRole = defaultUserRole;
    }
}
