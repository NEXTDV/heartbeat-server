package com.nextdv.infrastructure.healthcheck;

/**
 * class: ServiceStatusEntity 작성자: JBumLee
 *
 * DB 저장용 서비스 상태 열거형
 */
public enum ServiceStatusEntity {
  OPERATIONAL, DEGRADED, PARTIAL_OUTAGE, MAJOR_OUTAGE, UNKNOWN
}
