package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.platform.Platform;

public interface PlatformHealthChecker {

  boolean supports(Platform platform);

  HealthCheckResult check(Platform platform);
}
