package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.common.UuidV7;
import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.incident.Incident;
import com.nextdv.domain.incident.IncidentRepository;
import com.nextdv.domain.incident.IncidentStatus;
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
  private final IncidentRepository incidentRepository;

  public void pollAll() {
    platformService.findAll().forEach(this::poll);
  }

  private void poll(Platform platform) {
    long startMs = System.currentTimeMillis();
    ServiceStatus status;
    long responseMs;
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
      responseMs = System.currentTimeMillis() - startMs;
      status = determineStatus(
          response.getStatusCode().value(),
          responseMs,
          platform.getDegradedThresholdMs()
      );
    } catch (ResourceAccessException e) {
      responseMs = System.currentTimeMillis() - startMs;
      status = ServiceStatus.MAJOR_OUTAGE;
    }

    healthCheckLogRepository.save(
        new HealthCheckLog(
            UUID.randomUUID(), platform.getId(), status, responseMs, Instant.now()
        )
    );

    handleIncident(
        platform.getId(),
        status
    );
  }

  private void handleIncident(UUID platformId, ServiceStatus status) {
    if (status == ServiceStatus.PARTIAL_OUTAGE || status == ServiceStatus.MAJOR_OUTAGE) {
      incidentRepository.findOpenByPlatformId(platformId).ifPresentOrElse(
          existing -> {
          },
          () -> incidentRepository.save(
              new Incident(
                  UuidV7.generate(), platformId, status, IncidentStatus.OPEN,
                  Instant.now(), null
              )
          )
      );
    } else {
      incidentRepository.findOpenByPlatformId(platformId).ifPresent(
          open -> incidentRepository.save(
              new Incident(
                  open.getId(), open.getPlatformId(), open.getImpact(),
                  IncidentStatus.RESOLVED, open.getStartedAt(), Instant.now()
              )
          )
      );
    }
  }

  ServiceStatus determineStatus(int httpStatus, long responseMs, int degradedThresholdMs) {
    if (httpStatus >= 500)
      return ServiceStatus.MAJOR_OUTAGE;
    if (responseMs >= degradedThresholdMs)
      return ServiceStatus.DEGRADED;
    return ServiceStatus.OPERATIONAL;
  }
}
