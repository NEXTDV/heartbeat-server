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

/**
 * 클래스명: HealthController
 * 작성자: JBumLee
 *
 * 서버 헬스체크 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

  private final HealthService healthService;

  /**
   * 메소드이름: health
   * 서버가 정상 동작 중인지 확인하는 헬스체크를 수행한다
   *
   * @return 서버 상태 응답
   */
  @GetMapping
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<HealthResponse>> health() {
    HealthResult result = healthService.check();
    return ResponseEntity.ok(CommonResponse.ok(HealthMapper.toResponse(result)));
  }
}
