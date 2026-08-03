package com.nextdv.domain.incident;

import com.nextdv.domain.healthcheck.ServiceStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Incident {

  private final UUID id;
  private final UUID platformId;
  private final ServiceStatus impact;
  private final IncidentStatus status;
  private final Instant startedAt;
  private final Instant resolvedAt;
}
