package com.nextdv.api.healthcheck;

import com.nextdv.api.common.CommonResponse;
import com.nextdv.domain.healthcheck.HealthCheckLogService;
import com.nextdv.domain.platform.PlatformService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 클래스명: PlatformStatusController
 * 작성자: JBumLee
 *
 * 플랫폼 헬스체크 상태 조회 REST API 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformStatusController {

  private final HealthCheckLogService healthCheckLogService;
  private final PlatformService platformService;

  /**
   * 메소드이름: getStatus
   * 특정 플랫폼의 최신 헬스체크 상태를 조회한다
   *
   * @param id 조회할 플랫폼 UUID
   * @return 가장 최근 헬스체크 상태 응답
   */
  @GetMapping("/{id}/status")
  @ApiResponse(responseCode = "400", description = "id가 UUID 형식이 아님")
  @ApiResponse(responseCode = "404", description = "해당 ID에 해당하는 플랫폼이 존재하지 않음")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<HealthCheckLogResponse>> getStatus(@PathVariable UUID id) {
    platformService
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("플랫폼을 찾을 수 없습니다."));
    return healthCheckLogService
        .findLatestByPlatformId(id)
        .map(log -> ResponseEntity.ok(CommonResponse.ok(HealthCheckLogMapper.toResponse(log))))
        .orElse(ResponseEntity.ok(CommonResponse.ok(null)));
  }

  /**
   * 메소드이름: getLogs
   * 특정 플랫폼의 모든 헬스체크 로그를 조회한다
   *
   * @param id 조회할 플랫폼 UUID
   * @return 전체 헬스체크 로그 응답 목록
   */
  @GetMapping("/{id}/logs")
  @ApiResponse(responseCode = "400", description = "id가 UUID 형식이 아님")
  @ApiResponse(responseCode = "404", description = "해당 ID에 해당하는 플랫폼이 존재하지 않음")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<List<HealthCheckLogResponse>>> getLogs(
      @PathVariable UUID id) {
    platformService
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("플랫폼을 찾을 수 없습니다."));
    return ResponseEntity.ok(
        CommonResponse.ok(
            HealthCheckLogMapper.toResponseList(healthCheckLogService.findAllByPlatformId(id))
        )
    );
  }
}
