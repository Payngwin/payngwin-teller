package com.mungwin.payngwinteller.domain.repository.account;

import com.mungwin.payngwinteller.domain.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByUserIdAndAccountType(UUID userId, String accountType);
    List<Account> findAllByUserId(UUID userId);
    Optional<Account> findFirstByIdAndUserId(Long id, UUID userId);
    List<Account> findAllByAccountType(String s);
}
