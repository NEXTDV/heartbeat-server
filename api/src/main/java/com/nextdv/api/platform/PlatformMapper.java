package com.nextdv.api.platform;

import com.nextdv.domain.platform.Platform;
import java.util.List;

public class PlatformMapper {

  private PlatformMapper() {
  }

  public static PlatformResponse toResponse(Platform platform) {
    return new PlatformResponse(
        platform.getId(),
        platform.getName(),
        platform.getCategory(),
        platform.getHealthCheckUrl(),
        platform.getTimeoutMs(),
        platform.getDegradedThresholdMs(),
        platform.getIconUrl(),
        platform.isActive()
    );
  }

  public static List<PlatformResponse> toResponseList(
      List<Platform> platforms) {
    return platforms.stream().map(PlatformMapper::toResponse).toList();
  }
}
