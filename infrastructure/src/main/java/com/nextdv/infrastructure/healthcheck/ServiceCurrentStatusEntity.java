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
@Table(name = "service_current_status")
public class ServiceCurrentStatusEntity {

  @Id
  @Column(name = "platform_id", nullable = false)
  private UUID platformId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ServiceStatusEntity status;

  @Column(name = "response_ms", nullable = false)
  private long responseMs;

  @Column(name = "last_checked_at", nullable = false)
  private Instant lastCheckedAt;

  public ServiceCurrentStatusEntity(
      UUID platformId, ServiceStatusEntity status, long responseMs, Instant lastCheckedAt) {
    this.platformId = platformId;
    this.status = status;
    this.responseMs = responseMs;
    this.lastCheckedAt = lastCheckedAt;
  }

  public void update(ServiceStatusEntity status, long responseMs, Instant lastCheckedAt) {
    this.status = status;
    this.responseMs = responseMs;
    this.lastCheckedAt = lastCheckedAt;
  }
}
