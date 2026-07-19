package com.nextdv.domain.healthcheck;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class HealthCheckLog {

  private final UUID id;
  private final UUID platformId;
  private final ServiceStatus status;
  private final long responseTimeMs;
  private final Instant checkedAt;

  public HealthCheckLog(
      UUID id, UUID platformId, ServiceStatus status, long responseTimeMs, Instant checkedAt) {
    this.id = id;
    this.platformId = platformId;
    this.status = status;
    this.responseTimeMs = responseTimeMs;
    this.checkedAt = checkedAt;
  }
}
