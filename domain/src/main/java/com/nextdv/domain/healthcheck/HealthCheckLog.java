package com.nextdv.domain.healthcheck;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HealthCheckLog {

  private final UUID id;
  private final UUID platformId;
  private final ServiceStatus status;
  private final long responseTimeMs;
  private final Instant checkedAt;
}
