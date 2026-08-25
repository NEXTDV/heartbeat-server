package com.nextdv.infrastructure.demo;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import com.nextdv.infrastructure.healthcheck.HealthCheckResult;
import com.nextdv.infrastructure.healthcheck.PlatformHealthChecker;
import java.time.Instant;
import java.util.Random;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
class DemoPlatformHealthChecker implements PlatformHealthChecker {

  private static final String DEMO_URL = "demo://test";

  @Override
  public boolean supports(Platform platform) {
    return DEMO_URL.equals(platform.getHealthCheckUrl());
  }

  @Override
  public HealthCheckResult check(Platform platform) {
    long windowIndex = Instant.now().getEpochSecond() / 600;
    return new HealthCheckResult(computeStatus(windowIndex), null, null);
  }

  ServiceStatus computeStatus(long windowIndex) {
    int idx = new Random(windowIndex).nextInt(3);
    return switch (idx) {
      case 1 -> ServiceStatus.DEGRADED;
      case 2 -> ServiceStatus.MAJOR_OUTAGE;
      default -> ServiceStatus.OPERATIONAL;
    };
  }
}
