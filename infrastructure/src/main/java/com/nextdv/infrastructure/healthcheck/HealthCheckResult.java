package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.healthcheck.ServiceStatus;

public record HealthCheckResult(
    ServiceStatus status, Integer httpStatusCode, Integer responseTimeMs) {
}
