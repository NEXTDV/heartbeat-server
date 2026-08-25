package com.nextdv.domain.healthcheck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 클래스명: HealthCheckLogRepository
 * 작성자: JBumLee
 *
 * 헬스체크 로그 도메인 저장소 인터페이스
 */
public interface HealthCheckLogRepository {

  /**
   * 메소드이름: save
   * 헬스체크 로그를 저장한다
   *
   * @param log 저장할 헬스체크 로그 객체
   * @return 저장된 헬스체크 로그 객체
   */
  HealthCheckLog save(HealthCheckLog log);

  /**
   * 메소드이름: findAllByPlatformId
   * 특정 플랫폼의 모든 헬스체크 로그를 조회한다
   *
   * @param platformId 조회할 플랫폼 ID
   * @return 해당 플랫폼의 전체 헬스체크 로그 목록
   */
  List<HealthCheckLog> findAllByPlatformId(UUID platformId);

  /**
   * 메소드이름: findLatestByPlatformId
   * 특정 플랫폼의 가장 최근 헬스체크 로그를 조회한다
   *
   * @param platformId 조회할 플랫폼 ID
   * @return 가장 최근 헬스체크 로그 (없으면 empty)
   */
  Optional<HealthCheckLog> findLatestByPlatformId(UUID platformId);
}
