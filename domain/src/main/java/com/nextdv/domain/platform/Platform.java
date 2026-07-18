package com.nextdv.domain.platform;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Platform {

  private final UUID id;
  private final String name;
  private final ServiceCategory category;
  private final String healthCheckUrl;
  private final int timeoutMs;
  private final int degradedThresholdMs;
  private final String iconUrl;
  private final boolean active;

  public Platform(
      UUID id,
      String name,
      ServiceCategory category,
      String healthCheckUrl,
      int timeoutMs,
      int degradedThresholdMs,
      String iconUrl,
      boolean active) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.healthCheckUrl = healthCheckUrl;
    this.timeoutMs = timeoutMs;
    this.degradedThresholdMs = degradedThresholdMs;
    this.iconUrl = iconUrl;
    this.active = active;
  }

}
