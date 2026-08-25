package com.nextdv.api.healthcheck;

import com.nextdv.domain.healthcheck.HealthCheckLog;
import java.util.List;

/**
 * class: HealthCheckLogMapper 작성자: JBumLee
 *
 * HealthCheckLog 도메인 객체를 HealthCheckLogResponse DTO로 변환하는 매퍼
 */
public class HealthCheckLogMapper {

  /**
   * 메소드이름: toResponse
   *
   * @parameter: log - 변환할 헬스체크 로그 도메인 객체
   * @return: HealthCheckLogResponse DTO
   *
   *          HealthCheckLog 도메인 객체를 응답 DTO로 변환한다
   */
  public static HealthCheckLogResponse toResponse(HealthCheckLog log) {
    return new HealthCheckLogResponse(
        log.getId(),
        log.getPlatformId(),
        log.getStatus(),
        log.getResponseTimeMs(),
        log.getCheckedAt()
    );
  }

  /**
   * 메소드이름: toResponseList
   *
   * @parameter: logs - 변환할 헬스체크 로그 도메인 객체 목록
   * @return: HealthCheckLogResponse DTO 목록
   *
   *          HealthCheckLog 도메인 객체 목록을 응답 DTO 목록으로 변환한다
   */
  public static List<HealthCheckLogResponse> toResponseList(List<HealthCheckLog> logs) {
    return logs.stream().map(HealthCheckLogMapper::toResponse).toList();
  }
}
