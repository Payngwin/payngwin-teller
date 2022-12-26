package com.mungwin.payngwinteller.domain.repository.account;

import com.mungwin.payngwinteller.domain.model.account.AccountBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBalanceHistoryRepository extends JpaRepository<AccountBalanceHistory, Long> {
}
