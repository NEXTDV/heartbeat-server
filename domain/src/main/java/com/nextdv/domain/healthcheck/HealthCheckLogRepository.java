package com.nextdv.domain.healthcheck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HealthCheckLogRepository {

  HealthCheckLog save(HealthCheckLog log);

  List<HealthCheckLog> findAllByPlatformId(UUID platformId);

  Optional<HealthCheckLog> findLatestByPlatformId(UUID platformId);
}
