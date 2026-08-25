package com.nextdv.api.healthcheck;

import com.nextdv.domain.healthcheck.ServiceStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: HealthCheckLogResponse 작성자: JBumLee
 *
 * 헬스체크 로그 응답 DTO
 */
@Getter
@AllArgsConstructor
public class HealthCheckLogResponse {

  private UUID id;
  private UUID platformId;
  private ServiceStatus status;
  private Integer responseMs;
  private Instant createdAt;
}
