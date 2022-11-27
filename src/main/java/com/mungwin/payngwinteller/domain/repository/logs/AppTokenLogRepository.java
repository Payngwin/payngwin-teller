package com.mungwin.payngwinteller.domain.repository.logs;

import com.mungwin.payngwinteller.domain.model.logs.AppTokenLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppTokenLogRepository extends JpaRepository<AppTokenLog, UUID> {
}
