package com.nextdv.infrastructure.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * class: HealthCheckScheduler 작성자: JBumLee
 *
 * 주기적으로 헬스체크를 실행하는 스케줄러
 */
@Component
@RequiredArgsConstructor
public class HealthCheckScheduler {

  private final HealthCheckPollService healthCheckPollService;

  /**
   * 메소드이름: run
   *
   * 60초마다 모든 플랫폼에 대해 헬스체크를 실행한다
   */
  @Scheduled(fixedDelay = 60_000)
  public void run() {
    healthCheckPollService.pollAll();
  }
}
