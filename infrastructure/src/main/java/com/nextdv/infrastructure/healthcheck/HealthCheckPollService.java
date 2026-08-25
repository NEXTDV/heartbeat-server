package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.channel.ChannelRepository;
import com.nextdv.domain.channel.DiscordSender;
import com.nextdv.domain.channel.EmailSender;
import com.nextdv.domain.channel.SlackSender;
import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthCheckPollService {

  private final PlatformService platformService;
  private final HealthCheckLogRepository healthCheckLogRepository;
  private final List<PlatformHealthChecker> healthCheckers;
  private final ChannelRepository channelRepository;
  private final EmailSender emailSender;
  private final SlackSender slackSender;
  private final DiscordSender discordSender;

  public void pollAll() {
    platformService.findAll().forEach(this::poll);
  }

  private void poll(Platform platform) {
    ServiceStatus previousStatus = healthCheckLogRepository
        .findLatestByPlatformId(platform.getId())
        .map(HealthCheckLog::getStatus)
        .orElse(null);

    HealthCheckResult result = healthCheckers.stream()
        .filter(c -> c.supports(platform))
        .findFirst()
        .orElseThrow()
        .check(platform);

    notifyStatusChange(
        platform,
        previousStatus,
        result.status()
    );

    healthCheckLogRepository.save(
        new HealthCheckLog(
            UUID.randomUUID(),
            platform.getId(),
            result.status(),
            result.httpStatusCode(),
            result.responseTimeMs(),
            Instant.now()
        )
    );
  }

  void notifyStatusChange(Platform platform, ServiceStatus previous, ServiceStatus current) {
    if (previous == null || previous == current) {
      return;
    }
    channelRepository
        .findEmailChannelsByPlatformId(platform.getId())
        .forEach(
            channel -> {
              String address = (String) channel.getConfig().get("address");
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
            }
        );
    channelRepository
        .findSlackChannelsByPlatformId(platform.getId())
        .forEach(
            channel -> {
              String url = (String) channel.getConfig().get("url");
              try {
                slackSender.send(
                    url,
                    platform,
                    current
                );
              } catch (Exception e) {
                log.error(
                    "Slack 발송 실패 — 채널: {}, URL: {}",
                    channel.getId(),
                    url,
                    e
                );
              }
            }
        );
    channelRepository
        .findDiscordChannelsByPlatformId(platform.getId())
        .forEach(
            channel -> {
              String url = (String) channel.getConfig().get("url");
              try {
                discordSender.send(
                    url,
                    platform,
                    current
                );
              } catch (Exception e) {
                log.error(
                    "Discord 발송 실패 — 채널: {}, URL: {}",
                    channel.getId(),
                    url,
                    e
                );
              }
            }
        );
  }
}
