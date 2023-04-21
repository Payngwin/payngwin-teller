package com.mungwin.payngwinteller.domain.repository.payment;

import com.mungwin.payngwinteller.domain.model.payment.PaymentProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentProviderRepository extends JpaRepository<PaymentProvider, String> {
    Optional<PaymentProvider> findFirstByName(String n);
}
