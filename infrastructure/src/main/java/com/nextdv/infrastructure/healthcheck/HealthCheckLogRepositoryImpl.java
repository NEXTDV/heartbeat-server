package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 클래스명: HealthCheckLogRepositoryImpl
 * 작성자: JBumLee
 *
 * HealthCheckLogRepository 인터페이스의 JPA 기반 구현체
 */
@Repository
@RequiredArgsConstructor
public class HealthCheckLogRepositoryImpl implements HealthCheckLogRepository {

  private final HealthCheckLogJpaRepository jpaRepository;

  @Override
  public HealthCheckLog save(HealthCheckLog log) {
    HealthCheckLogEntity entity = new HealthCheckLogEntity(
        log.getId(),
        log.getPlatformId(),
        ServiceStatusEntity.valueOf(log.getStatus().name()),
        log.getHttpStatusCode(),
        log.getResponseTimeMs(),
        log.getCheckedAt()
    );
    return toDomain(jpaRepository.save(entity));
  }

  @Override
  public List<HealthCheckLog> findAllByPlatformId(UUID platformId) {
    return jpaRepository.findAllByPlatformId(platformId).stream().map(this::toDomain).toList();
  }

  @Override
  public Optional<HealthCheckLog> findLatestByPlatformId(UUID platformId) {
    return jpaRepository.findTopByPlatformIdOrderByCheckedAtDesc(platformId).map(this::toDomain);
  }

  private HealthCheckLog toDomain(HealthCheckLogEntity entity) {
    return new HealthCheckLog(
        entity.getId(),
        entity.getPlatformId(),
        ServiceStatus.valueOf(entity.getStatus().name()),
        entity.getHttpStatusCode(),
        entity.getResponseTimeMs(),
        entity.getCheckedAt()
    );
  }
}
