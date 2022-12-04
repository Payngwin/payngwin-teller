package com.mungwin.payngwinteller.domain.repository.iam;

import com.mungwin.payngwinteller.domain.model.iam.AppToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AppTokenRepository extends JpaRepository<AppToken, UUID> {
    Optional<AppToken> findFirstByToken(String token);
    @Modifying
    @Query("DELETE FROM AppToken a where a.appId = ?1")
    void deleteAllByAppId(UUID appId);
}
