package com.mungwin.payngwinteller.domain.repository.payment;

import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionOrderRepository extends JpaRepository<CollectionOrder, Long> {
    Optional<CollectionOrder> findFirstByExternalIdAndAccountIdAndTag(String externalId, Long accountId, String tag);
}
