package com.nextdv.domain.health;

import org.springframework.stereotype.Service;

/**
 * class: HealthService 작성자: JBumLee
 *
 * 서버 헬스체크 상태를 반환하는 서비스
 */
@Service
public class HealthService {

  /**
   * 메소드이름: check
   *
   * @return: 서버 헬스체크 결과
   *
   *          서버가 정상 동작 중임을 확인하는 헬스체크를 수행한다
   */
  public HealthResult check() {
    return new HealthResult("ok");
  }
}
