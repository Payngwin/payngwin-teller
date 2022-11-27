package com.mungwin.payngwinteller.domain.repository.iam;

import com.mungwin.payngwinteller.domain.model.iam.AppToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppTokenRepository extends JpaRepository<AppToken, UUID> {
    Optional<AppToken> findFirstByToken(String token);
}
