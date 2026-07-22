package com.nextdv.api.healthcheck;

import com.nextdv.domain.healthcheck.HealthCheckLog;
import java.util.List;

public class HealthCheckLogMapper {

  public static HealthCheckLogResponse toResponse(HealthCheckLog log) {
    return new HealthCheckLogResponse(
        log.getId(),
        log.getPlatformId(),
        log.getStatus(),
        log.getResponseTimeMs(),
        log.getCheckedAt()
    );
  }

  public static List<HealthCheckLogResponse> toResponseList(List<HealthCheckLog> logs) {
    return logs.stream().map(HealthCheckLogMapper::toResponse).toList();
  }
}
