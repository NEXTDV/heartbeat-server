package com.nextdv.api.healthcheck;

import com.nextdv.domain.healthcheck.ServiceStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HealthCheckLogResponse {

  private UUID id;
  private UUID platformId;
  private ServiceStatus status;
  private long responseMs;
  private Instant createdAt;
}
