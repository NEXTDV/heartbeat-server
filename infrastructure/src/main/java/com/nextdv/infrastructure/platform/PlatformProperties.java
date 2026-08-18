package com.nextdv.infrastructure.platform;

import com.nextdv.domain.platform.ServiceCategory;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("heartbeat")
public record PlatformProperties(List<PlatformConfig> platforms) {

  public record PlatformConfig(
      String name,
      ServiceCategory category,
      String healthCheckUrl,
      int timeoutMs,
      int degradedThresholdMs,
      String iconUrl) {
  }
}
