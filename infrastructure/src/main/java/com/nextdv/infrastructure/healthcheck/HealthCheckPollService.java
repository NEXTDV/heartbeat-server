package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class HealthCheckPollService {

  private final PlatformService platformService;
  private final HealthCheckLogRepository healthCheckLogRepository;
  private final RestClient healthCheckRestClient;

  public void pollAll() {
    platformService.findAll().forEach(this::poll);
  }

  private void poll(Platform platform) {
    long startMs = System.currentTimeMillis();
    ServiceStatus status;
    Integer responseMs;
    Integer httpStatusCode;
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
      responseMs = (int) (System.currentTimeMillis() - startMs);
      httpStatusCode = response.getStatusCode().value();
      status = determineStatus(
          httpStatusCode,
          responseMs,
          platform.getDegradedThresholdMs()
      );
    } catch (ResourceAccessException e) {
      responseMs = (int) (System.currentTimeMillis() - startMs);
      httpStatusCode = null;
      status = ServiceStatus.MAJOR_OUTAGE;
    }

    healthCheckLogRepository.save(
        new HealthCheckLog(
            UUID.randomUUID(), platform.getId(), status, httpStatusCode, responseMs, Instant.now()
        )
    );
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
