package com.nextdv.domain.health;

/**
 * class: HealthResult 작성자: JBumLee
 *
 * 서버 헬스체크 결과 도메인 객체
 */
public class HealthResult {

  private final String status;

  public HealthResult(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
