package com.nextdv.api.health;

/**
 * 클래스명: HealthResponse
 * 작성자: JBumLee
 *
 * 서버 헬스체크 응답 DTO
 */
public class HealthResponse {

  private final String status;

  public HealthResponse(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
