package com.nextdv.api.health;

import com.nextdv.api.common.ApiResponse;
import com.nextdv.domain.health.HealthResult;
import com.nextdv.domain.health.HealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

  private final HealthService healthService;

  public HealthController(HealthService healthService) {
    this.healthService = healthService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<HealthResponse>> health() {
    HealthResult result = healthService.check();
    return ResponseEntity.ok(
        ApiResponse.ok(
            HealthMapper.toResponse(
                result
            )
        )
    );
  }
}
