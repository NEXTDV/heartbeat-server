package com.nextdv.domain.healthcheck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 클래스명: HealthCheckLogService
 * 작성자: JBumLee
 *
 * 헬스체크 로그 조회 비즈니스 로직을 담당하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HealthCheckLogService {

  private final HealthCheckLogRepository healthCheckLogRepository;

  /**
   * 메소드이름: findLatestByPlatformId
   * 특정 플랫폼의 최신 헬스체크 로그를 조회한다
   *
   * @param platformId 조회할 플랫폼 ID
   * @return 가장 최근 헬스체크 로그 (없으면 empty)
   */
  public Optional<HealthCheckLog> findLatestByPlatformId(UUID platformId) {
    log.info(
        "최신 헬스체크 로그 조회 — platformId: {}",
        platformId
    );
    return healthCheckLogRepository.findLatestByPlatformId(platformId);
  }

  /**
   * 메소드이름: findAllByPlatformId
   * 특정 플랫폼의 모든 헬스체크 로그를 조회한다
   *
   * @param platformId 조회할 플랫폼 ID
   * @return 해당 플랫폼의 전체 헬스체크 로그 목록
   */
  public List<HealthCheckLog> findAllByPlatformId(UUID platformId) {
    List<HealthCheckLog> logs = healthCheckLogRepository.findAllByPlatformId(platformId);
    log.info(
        "헬스체크 로그 전체 조회 — platformId: {}, 건수: {}",
        platformId,
        logs.size()
    );
    return logs;
  }
}
