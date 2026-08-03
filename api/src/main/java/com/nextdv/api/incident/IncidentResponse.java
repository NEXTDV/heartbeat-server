package com.nextdv.api.incident;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.incident.IncidentStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncidentResponse {

  private final UUID id;
  private final UUID platformId;
  private final ServiceStatus impact;
  private final IncidentStatus status;
  private final Instant startedAt;
  private final Instant resolvedAt;
}
