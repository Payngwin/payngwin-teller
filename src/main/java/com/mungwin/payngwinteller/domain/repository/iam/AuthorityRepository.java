package com.mungwin.payngwinteller.domain.repository.iam;

import com.mungwin.payngwinteller.domain.model.iam.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
}
