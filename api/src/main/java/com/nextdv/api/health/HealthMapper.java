package com.nextdv.api.health;

import com.nextdv.domain.health.HealthResult;

public class HealthMapper {

  private HealthMapper() {}

  public static HealthResponse toResponse(HealthResult result) {
    return new HealthResponse(result.getStatus());
  }
}
