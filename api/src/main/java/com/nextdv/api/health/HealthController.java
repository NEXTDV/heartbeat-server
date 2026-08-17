package com.nextdv.api.health;

import com.nextdv.api.common.CommonResponse;
import com.nextdv.domain.health.HealthResult;
import com.nextdv.domain.health.HealthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

  private final HealthService healthService;

  @GetMapping
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<HealthResponse>> health() {
    HealthResult result = healthService.check();
    return ResponseEntity.ok(CommonResponse.ok(HealthMapper.toResponse(result)));
  }
}
