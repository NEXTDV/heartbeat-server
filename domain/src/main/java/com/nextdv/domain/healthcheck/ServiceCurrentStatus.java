package com.nextdv.domain.healthcheck;

import java.time.Instant;
import java.util.UUID;

public record ServiceCurrentStatus(
    UUID platformId, ServiceStatus status, long responseTimeMs, Instant lastCheckedAt) {
}
