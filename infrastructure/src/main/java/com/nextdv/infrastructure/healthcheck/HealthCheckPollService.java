package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.channel.Channel;
import com.nextdv.domain.channel.ChannelRepository;
import com.nextdv.domain.channel.EmailSender;
import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthCheckPollService {

  private final PlatformService platformService;
  private final HealthCheckLogRepository healthCheckLogRepository;
  private final RestClient healthCheckRestClient;
  private final ChannelRepository channelRepository;
  private final EmailSender emailSender;

  public void pollAll() {
    platformService.findAll().forEach(this::poll);
  }

  private void poll(Platform platform) {
    ServiceStatus previousStatus = healthCheckLogRepository
        .findLatestByPlatformId(platform.getId())
        .map(HealthCheckLog::getStatus)
        .orElse(null);

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

    notifyStatusChange(
        platform,
        previousStatus,
        status
    );

    healthCheckLogRepository.save(
        new HealthCheckLog(
            UUID.randomUUID(), platform.getId(), status, httpStatusCode, responseMs, Instant.now()
        )
    );
  }

  void notifyStatusChange(Platform platform, ServiceStatus previous, ServiceStatus current) {
    if (previous == null || previous == current) {
      return;
    }
    channelRepository.findEmailChannelsByPlatformId(platform.getId()).forEach(channel -> {
      String address = extractAddress(channel);
      if (address == null) {
        return;
      }
      try {
        emailSender.send(
            address,
            platform,
            current
        );
      } catch (Exception e) {
        log.error(
            "이메일 발송 실패 — 채널: {}, 주소: {}",
            channel.getId(),
            address,
            e
        );
      }
    });
  }

  private String extractAddress(Channel channel) {
    Object value = channel.getConfig().get("address");
    return value instanceof String s ? s : null;
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
