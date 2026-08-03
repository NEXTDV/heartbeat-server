package com.nextdv.infrastructure.incident;

import com.nextdv.infrastructure.healthcheck.ServiceStatusEntity;
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
@Table(name = "incidents")
public class IncidentEntity {

  @Id
  private UUID id;

  @Column(name = "platform_id", nullable = false)
  private UUID platformId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ServiceStatusEntity impact;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private IncidentStatusEntity status;

  @Column(name = "started_at", nullable = false)
  private Instant startedAt;

  @Column(name = "resolved_at")
  private Instant resolvedAt;

  public IncidentEntity(
      UUID id,
      UUID platformId,
      ServiceStatusEntity impact,
      IncidentStatusEntity status,
      Instant startedAt,
      Instant resolvedAt) {
    this.id = id;
    this.platformId = platformId;
    this.impact = impact;
    this.status = status;
    this.startedAt = startedAt;
    this.resolvedAt = resolvedAt;
  }

  public void update(IncidentStatusEntity status, Instant resolvedAt) {
    this.status = status;
    this.resolvedAt = resolvedAt;
  }
}
