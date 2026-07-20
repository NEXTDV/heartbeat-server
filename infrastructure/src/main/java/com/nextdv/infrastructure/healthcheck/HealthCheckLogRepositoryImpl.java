package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HealthCheckLogRepositoryImpl implements HealthCheckLogRepository {

  private final HealthCheckLogJpaRepository jpaRepository;

  @Override
  public HealthCheckLog save(HealthCheckLog log) {
    HealthCheckLogEntity entity = new HealthCheckLogEntity(
        log.getId(), log.getPlatformId(), log.getStatus(),
        log.getResponseTimeMs(), log.getCheckedAt()
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
        entity.getStatus(),
        entity.getResponseTimeMs(),
        entity.getCheckedAt()
    );
  }
}
