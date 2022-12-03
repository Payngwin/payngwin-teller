package com.mungwin.payngwinteller.security.security.auditing;

import com.mungwin.payngwinteller.security.security.AppSecurityContextHolder;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

public class AuditService implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        if (!AppSecurityContextHolder.shouldFailIfAbsent()) {
            return Optional.empty();
        }
        return Optional.of(AppSecurityContextHolder.getPrincipal().getUser().getId());
    }
}
