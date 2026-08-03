package com.nextdv.infrastructure.incident;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.incident.Incident;
import com.nextdv.domain.incident.IncidentRepository;
import com.nextdv.domain.incident.IncidentStatus;
import com.nextdv.infrastructure.healthcheck.ServiceStatusEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IncidentRepositoryImpl implements IncidentRepository {

  private final IncidentJpaRepository jpaRepository;

  @Override
  public Incident save(Incident incident) {
    IncidentStatusEntity statusEntity = IncidentStatusEntity.valueOf(incident.getStatus().name());
    ServiceStatusEntity impactEntity = ServiceStatusEntity.valueOf(incident.getImpact().name());

    jpaRepository.findById(incident.getId()).ifPresentOrElse(
        entity -> entity.update(
            statusEntity,
            incident.getResolvedAt()
        ),
        () -> jpaRepository.save(
            new IncidentEntity(
                incident.getId(),
                incident.getPlatformId(),
                impactEntity,
                statusEntity,
                incident.getStartedAt(),
                incident.getResolvedAt()
            )
        )
    );
    return incident;
  }

  @Override
  public Optional<Incident> findOpenByPlatformId(UUID platformId) {
    return jpaRepository
        .findByPlatformIdAndStatus(
            platformId,
            IncidentStatusEntity.OPEN
        )
        .map(this::toDomain);
  }

  @Override
  public List<Incident> findAll() {
    return jpaRepository.findAllByOrderByStartedAtDesc().stream()
        .map(this::toDomain)
        .toList();
  }

  @Override
  public Optional<Incident> findById(UUID id) {
    return jpaRepository.findById(id).map(this::toDomain);
  }

  private Incident toDomain(IncidentEntity entity) {
    return new Incident(
        entity.getId(),
        entity.getPlatformId(),
        ServiceStatus.valueOf(entity.getImpact().name()),
        IncidentStatus.valueOf(entity.getStatus().name()),
        entity.getStartedAt(),
        entity.getResolvedAt()
    );
  }
}
