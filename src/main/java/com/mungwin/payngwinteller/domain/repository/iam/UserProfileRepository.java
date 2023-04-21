package com.mungwin.payngwinteller.domain.repository.iam;

import com.mungwin.payngwinteller.domain.model.iam.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findFirstByUserId(String userId);
    Optional<UserProfile> findFirstByPhone(String phone);
}
