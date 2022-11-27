package com.mungwin.payngwinteller.security.security.aspects;


import com.mungwin.payngwinteller.security.exception.ApiException;
import com.mungwin.payngwinteller.security.security.AppSecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultApproveHandler implements HandlesApproval {
    public void doApprove(List<String> authorities) {
        try {
            // is user
            List<String> userAuthorities = AppSecurityContextHolder.getPrincipal()
                    .getRole().getAuthorities();
            // get all user authorities that ends with *
            List<String> groupAuthorities = userAuthorities.stream()
                    .filter(a -> a.endsWith("*")).map(a -> a.substring(0, a.length() - 1))
                    .collect(Collectors.toList());
            // policies
            boolean isAllowed = false;
            for (String authority: authorities) {
                if (authority.endsWith("*")) {
                    String starting = authority.substring(0, authority.length() - 1);
                    if (userAuthorities.stream().anyMatch(a -> a.startsWith(starting))) {
                        isAllowed = true;
                        break;
                    }
                } else {
                    if (userAuthorities.contains(authority)) {
                        isAllowed = true;
                        break;
                    } else {
                        if (groupAuthorities.stream().anyMatch(authority::startsWith)) {
                            isAllowed = true;
                            break;
                        }

                    }
                }
            }
            if (!isAllowed) {
                throw ApiException.USER_ACCESS_DENIED;
            }
        } catch (NullPointerException e) {
            throw ApiException.USER_UNAUTHORIZED;
        }
    }
}
