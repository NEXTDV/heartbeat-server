package com.nextdv.domain.healthcheck;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
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

  /**
   * 메소드이름: calculateUptimePercent
   * since 이후의 로그 중 OPERATIONAL 비율을 백분율로 계산한다
   *
   * @param platformId 조회할 플랫폼 ID
   * @param since 집계 시작 시점 (이 시점 이후의 로그만 포함)
   * @return 가동률 백분율 (해당 구간에 로그가 없으면 empty)
   */
  public OptionalDouble calculateUptimePercent(UUID platformId, Instant since) {
    List<HealthCheckLog> logs = healthCheckLogRepository.findAllByPlatformId(platformId).stream()
        .filter(log -> !log.getCheckedAt().isBefore(since))
        .toList();
    if (logs.isEmpty()) {
      return OptionalDouble.empty();
    }
    long operational = logs.stream()
        .filter(log -> log.getStatus() == ServiceStatus.OPERATIONAL)
        .count();
    return OptionalDouble.of(operational * 100.0 / logs.size());
  }
}
