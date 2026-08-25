package com.nextdv.api.health;

import com.nextdv.domain.health.HealthResult;

/**
 * 클래스명: HealthMapper
 * 작성자: JBumLee
 *
 * HealthResult 도메인 객체를 HealthResponse DTO로 변환하는 매퍼
 */
public class HealthMapper {

  private HealthMapper() {
  }

  /**
   * 메소드이름: toResponse
   * HealthResult 도메인 객체를 응답 DTO로 변환한다
   *
   * @param result 변환할 헬스체크 결과 도메인 객체
   * @return HealthResponse DTO
   */
  public static HealthResponse toResponse(HealthResult result) {
    return new HealthResponse(result.getStatus());
  }
}
