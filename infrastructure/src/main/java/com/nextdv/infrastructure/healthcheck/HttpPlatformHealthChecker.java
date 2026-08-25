package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
@Order(Integer.MAX_VALUE)
@RequiredArgsConstructor
class HttpPlatformHealthChecker implements PlatformHealthChecker {

  private final RestClient healthCheckRestClient;

  @Override
  public boolean supports(Platform platform) {
    return true;
  }

  @Override
  public HealthCheckResult check(Platform platform) {
    long startMs = System.currentTimeMillis();
    try {
      ResponseEntity<Void> response = healthCheckRestClient
          .get()
          .uri(platform.getHealthCheckUrl())
          .retrieve()
          .onStatus(
              HttpStatusCode::isError,
              (req, res) -> {
              }
          )
          .toBodilessEntity();
      int responseMs = (int) (System.currentTimeMillis() - startMs);
      int httpStatusCode = response.getStatusCode().value();
      return new HealthCheckResult(
          determineStatus(
              httpStatusCode,
              responseMs,
              platform.getDegradedThresholdMs()
          ),
          httpStatusCode,
          responseMs
      );
    } catch (ResourceAccessException e) {
      int responseMs = (int) (System.currentTimeMillis() - startMs);
      return new HealthCheckResult(ServiceStatus.MAJOR_OUTAGE, null, responseMs);
    }
  }

  ServiceStatus determineStatus(int httpStatus, int responseMs, int degradedThresholdMs) {
    if (httpStatus >= 500) {
      return ServiceStatus.MAJOR_OUTAGE;
    }
    if (responseMs >= degradedThresholdMs) {
      return ServiceStatus.DEGRADED;
    }
    return ServiceStatus.OPERATIONAL;
  }
}
