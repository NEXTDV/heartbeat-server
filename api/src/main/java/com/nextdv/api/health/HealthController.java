package com.nextdv.api.health;

import com.nextdv.api.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

  @GetMapping
  public ResponseEntity<ApiResponse<HealthResponse>> health() {
    return ResponseEntity.ok(ApiResponse.ok(HealthResponse.ok()));
  }
}
