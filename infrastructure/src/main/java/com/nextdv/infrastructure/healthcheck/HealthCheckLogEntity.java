package com.nextdv.infrastructure.healthcheck;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
@Table(name = "health_check_logs")
public class HealthCheckLogEntity {

  @Id
  private UUID id;

  @Column(name = "platform_id", nullable = false)
  private UUID platformId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ServiceStatusEntity status;

  @Column(name = "http_status_code")
  private Integer httpStatusCode;

  @Column(name = "response_ms")
  private Integer responseTimeMs;

  @Column(name = "created_at", nullable = false)
  private Instant checkedAt;

  public HealthCheckLogEntity(
      UUID id, UUID platformId, ServiceStatusEntity status, Integer httpStatusCode,
      Integer responseTimeMs, Instant checkedAt) {
    this.id = id;
    this.platformId = platformId;
    this.status = status;
    this.httpStatusCode = httpStatusCode;
    this.responseTimeMs = responseTimeMs;
    this.checkedAt = checkedAt;
  }
}
