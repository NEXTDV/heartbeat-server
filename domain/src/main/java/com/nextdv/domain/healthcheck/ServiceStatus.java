package com.nextdv.domain.healthcheck;

/**
 * 클래스명: ServiceStatus
 * 작성자: JBumLee
 *
 * 서비스 상태를 나타내는
 */
public enum ServiceStatus {
  /** 정상 운영 중 */
  OPERATIONAL,
  /** 응답 지연 등 성능 저하 */
  DEGRADED,
  /** 일부 기능 장애 */
  PARTIAL_OUTAGE,
  /** 전면 장애 */
  MAJOR_OUTAGE,
  /** 상태 미확인 */
  UNKNOWN
}
