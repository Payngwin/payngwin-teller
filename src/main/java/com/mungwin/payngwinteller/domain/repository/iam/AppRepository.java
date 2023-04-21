package com.mungwin.payngwinteller.domain.repository.iam;

import com.mungwin.payngwinteller.domain.model.iam.App;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppRepository extends JpaRepository<App, String> {
    List<App> findAllByUserId(String userId);
    Optional<App> findFirstByEmail(String email);
}
