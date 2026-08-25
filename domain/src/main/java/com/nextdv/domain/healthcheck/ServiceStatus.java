package com.nextdv.domain.healthcheck;

/**
 * class: ServiceStatus 작성자: JBumLee
 *
 * 서비스 상태를 나타내는 열거형
 */
public enum ServiceStatus {
  OPERATIONAL, DEGRADED, PARTIAL_OUTAGE, MAJOR_OUTAGE, UNKNOWN
}
