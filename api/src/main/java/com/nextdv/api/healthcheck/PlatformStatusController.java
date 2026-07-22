package com.nextdv.api.healthcheck;

import com.nextdv.api.common.ApiResponse;
import com.nextdv.domain.healthcheck.HealthCheckLogService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformStatusController {

  private final HealthCheckLogService healthCheckLogService;

  @GetMapping("/{id}/status")
  public ResponseEntity<ApiResponse<HealthCheckLogResponse>> getStatus(@PathVariable UUID id) {
    return healthCheckLogService
        .findLatestByPlatformId(id)
        .map(log -> ResponseEntity.ok(ApiResponse.ok(HealthCheckLogMapper.toResponse(log))))
        .orElse(ResponseEntity.ok(ApiResponse.ok(null)));
  }

  @GetMapping("/{id}/logs")
  public ResponseEntity<ApiResponse<List<HealthCheckLogResponse>>> getLogs(@PathVariable UUID id) {
    return ResponseEntity.ok(
        ApiResponse.ok(
            HealthCheckLogMapper.toResponseList(
                healthCheckLogService.findAllByPlatformId(id)
            )
        )
    );
  }
}
