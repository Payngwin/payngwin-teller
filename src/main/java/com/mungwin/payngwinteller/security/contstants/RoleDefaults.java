package com.mungwin.payngwinteller.security.contstants;


import com.mungwin.payngwinteller.domain.model.iam.Role;
import com.mungwin.payngwinteller.security.service.ACL;

import java.util.*;

public class RoleDefaults {
    // unique role ids, should be the same across app instances and deployments
    private static final String USER_ID = "7591f132-6068-4565-9c5d-660036999f58";
    private static final String MERCHANT_ID = "a24413cc-140d-4428-a16e-b3dfe5208e5d";
    private static final String ADMIN_ID = "351824d8-83fc-486b-8cfa-2e034eaf406b";
    private static final String PRESIDENT_ID = "a32d60cf-0ab1-4153-aabb-f1bb3ac453b9";
    // add a new role id here

    // repository
    private static final Map<String, Role> repository = buildRepository();
    public static Optional<Role> findById(String id) {
        if (repository.containsKey(id)) {
            return Optional.of(repository.get(id));
        }
        return Optional.empty();
    }
    // expose roles here
    public static final Role USER = repository.get(USER_ID);
    public static final Role MERCHANT = repository.get(MERCHANT_ID);
    public static final Role ADMIN = repository.get(ADMIN_ID);
    private static final Role PRESIDENT = repository.get(PRESIDENT_ID);

    private static Role generate(String id, String name, List<String> authorities, String description) {
        Role role = new Role();
        role.setId(UUID.fromString(id));
        role.setName(name);
        role.setAuthorities(authorities);
        role.setDescription(description);
        return role;
    }
    private static Map<String, Role> buildRepository() {
        Map<String, Role> repository = new HashMap<>();
        repository.put(USER_ID, generate(USER_ID, "USER",
                Arrays.asList(ACL.USER), "default user role"));
        repository.put(MERCHANT_ID, generate(MERCHANT_ID, "MERCHANT",
                Arrays.asList(ACL.MERCHANT), "default supplier role"));
        repository.put(ADMIN_ID, generate(ADMIN_ID, "ADMIN",
                Arrays.asList(ACL.ADMIN), "default admin role"));
        repository.put(PRESIDENT_ID, generate(PRESIDENT_ID, "PRESIDENT",
                Arrays.asList(ACL.PRESIDENT), "default president role"));
        // add your new role here
        return repository;
    }
}
