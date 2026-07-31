package com.nextdv.infrastructure.healthcheck;

import com.nextdv.domain.healthcheck.ServiceCurrentStatus;
import com.nextdv.domain.healthcheck.ServiceCurrentStatusRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ServiceCurrentStatusRepositoryImpl implements ServiceCurrentStatusRepository {

  private final ServiceCurrentStatusJpaRepository jpaRepository;

  @Override
  public void save(ServiceCurrentStatus status) {
    ServiceStatusEntity statusEntity = ServiceStatusEntity.valueOf(status.status().name());
    jpaRepository
        .findById(status.platformId())
        .ifPresentOrElse(
            entity -> entity.update(
                statusEntity,
                status.responseTimeMs(),
                status.lastCheckedAt()
            ),
            () -> jpaRepository.save(
                new ServiceCurrentStatusEntity(
                    status.platformId(),
                    statusEntity,
                    status.responseTimeMs(),
                    status.lastCheckedAt()
                )
            )
        );
  }

  @Override
  public Optional<ServiceCurrentStatus> findByPlatformId(UUID platformId) {
    return jpaRepository.findById(platformId).map(this::toDomain);
  }

  private ServiceCurrentStatus toDomain(ServiceCurrentStatusEntity entity) {
    return new ServiceCurrentStatus(
        entity.getPlatformId(),
        ServiceStatus.valueOf(entity.getStatus().name()),
        entity.getResponseMs(),
        entity.getLastCheckedAt()
    );
  }
}
