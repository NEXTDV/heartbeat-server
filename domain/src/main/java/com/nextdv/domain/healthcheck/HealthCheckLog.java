package com.nextdv.domain.healthcheck;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: HealthCheckLog 작성자: JBumLee
 *
 * 플랫폼 헬스체크 결과 로그 도메인 객체
 */
@Getter
@AllArgsConstructor
public class HealthCheckLog {

  private final UUID id;
  private final UUID platformId;
  private final ServiceStatus status;
  private final Integer httpStatusCode;
  private final Integer responseTimeMs;
  private final Instant checkedAt;
}
