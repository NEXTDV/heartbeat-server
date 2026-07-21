package com.nextdv.infrastructure.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HealthCheckScheduler {

  private final HealthCheckPollService healthCheckPollService;

  @Scheduled(fixedDelay = 60_000)
  public void run() {
    healthCheckPollService.pollAll();
  }
}
