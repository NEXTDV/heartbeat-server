package com.nextdv.infrastructure.platform;

import com.nextdv.domain.platform.ServiceCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "platforms")
public class PlatformEntity {

  @Id
  private UUID id;

  @Column(nullable = false, length = 100)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ServiceCategory category;

  @Column(name = "health_check_url", nullable = false, length = 500)
  private String healthCheckUrl;

  @Column(name = "timeout_ms", nullable = false)
  private int timeoutMs;

  @Column(name = "degraded_threshold_ms", nullable = false)
  private int degradedThresholdMs;

  @Column(name = "icon_url", length = 500)
  private String iconUrl;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  protected PlatformEntity() {
  }

  public PlatformEntity(
      UUID id,
      String name,
      ServiceCategory category,
      String healthCheckUrl,
      int timeoutMs,
      int degradedThresholdMs,
      String iconUrl,
      boolean isActive) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.healthCheckUrl = healthCheckUrl;
    this.timeoutMs = timeoutMs;
    this.degradedThresholdMs = degradedThresholdMs;
    this.iconUrl = iconUrl;
    this.isActive = isActive;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ServiceCategory getCategory() {
    return category;
  }

  public String getHealthCheckUrl() {
    return healthCheckUrl;
  }

  public int getTimeoutMs() {
    return timeoutMs;
  }

  public int getDegradedThresholdMs() {
    return degradedThresholdMs;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public boolean isActive() {
    return isActive;
  }
}
