package com.mungwin.payngwinteller.domain.repository.iam;

import com.mungwin.payngwinteller.domain.model.iam.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findFirstByName(String name);
}
