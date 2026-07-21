package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.healthcheck.ServiceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
@Entity
@Table(name = "health_check_logs")
public class HealthCheckLogEntity {

  @Id
  private UUID id;

  @Column(name = "platform_id", nullable = false)
  private UUID platformId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "service_status")
  private ServiceStatus status;

  @Column(name = "response_ms", nullable = false)
  private long responseTimeMs;

  @Column(name = "created_at", nullable = false)
  private Instant checkedAt;

  protected HealthCheckLogEntity() {
  }

  public HealthCheckLogEntity(
      UUID id, UUID platformId, ServiceStatus status, long responseTimeMs, Instant checkedAt) {
    this.id = id;
    this.platformId = platformId;
    this.status = status;
    this.responseTimeMs = responseTimeMs;
    this.checkedAt = checkedAt;
  }
}
