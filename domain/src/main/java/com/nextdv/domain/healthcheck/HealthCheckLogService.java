package com.nextdv.domain.healthcheck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthCheckLogService {

  private final HealthCheckLogRepository healthCheckLogRepository;

  public Optional<HealthCheckLog> findLatestByPlatformId(UUID platformId) {
    return healthCheckLogRepository.findLatestByPlatformId(platformId);
  }

  public List<HealthCheckLog> findAllByPlatformId(UUID platformId) {
    return healthCheckLogRepository.findAllByPlatformId(platformId);
  }
}
