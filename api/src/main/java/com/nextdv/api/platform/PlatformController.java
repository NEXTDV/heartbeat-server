package com.nextdv.api.platform;

import com.nextdv.api.common.ApiResponse;
import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformController {

  private static final int DEFAULT_TIMEOUT_MS = 3000;
  private static final int DEFAULT_DEGRADED_THRESHOLD_MS = 1000;

  private final PlatformService platformService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<PlatformResponse>>> list() {
    List<Platform> platforms = platformService.findAll();
    return ResponseEntity.ok(
        ApiResponse.ok(
            PlatformMapper.toResponseList(
                platforms
            )
        )
    );
  }

  @PostMapping
  public ResponseEntity<ApiResponse<PlatformResponse>> register(
      @RequestBody PlatformRegisterRequest request) {
    int timeoutMs = DEFAULT_TIMEOUT_MS;
    if (request.getTimeoutMs() != null) {
      timeoutMs = request.getTimeoutMs();
    }
    int degradedThresholdMs = DEFAULT_DEGRADED_THRESHOLD_MS;
    if (request.getDegradedThresholdMs() != null) {
      degradedThresholdMs = request.getDegradedThresholdMs();
    }
    Platform platform = platformService.register(
        request.getName(),
        request.getCategory(),
        request.getHealthCheckUrl(),
        timeoutMs,
        degradedThresholdMs,
        request.getIconUrl()
    );
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ApiResponse.ok(
                PlatformMapper.toResponse(
                    platform
                )
            )
        );
  }
}
