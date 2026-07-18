package com.nextdv.api.platform;

import com.nextdv.domain.platform.ServiceCategory;

public class PlatformRegisterRequest {

  private String name;
  private ServiceCategory category;
  private String healthCheckUrl;
  private Integer timeoutMs;
  private Integer degradedThresholdMs;
  private String iconUrl;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ServiceCategory getCategory() {
    return category;
  }

  public void setCategory(ServiceCategory category) {
    this.category = category;
  }

  public String getHealthCheckUrl() {
    return healthCheckUrl;
  }

  public void setHealthCheckUrl(String healthCheckUrl) {
    this.healthCheckUrl = healthCheckUrl;
  }

  public Integer getTimeoutMs() {
    return timeoutMs;
  }

  public void setTimeoutMs(Integer timeoutMs) {
    this.timeoutMs = timeoutMs;
  }

  public Integer getDegradedThresholdMs() {
    return degradedThresholdMs;
  }

  public void setDegradedThresholdMs(Integer degradedThresholdMs) {
    this.degradedThresholdMs = degradedThresholdMs;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }
}
