package com.mungwin.payngwinteller.domain.repository.payment;

import com.mungwin.payngwinteller.domain.model.payment.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
