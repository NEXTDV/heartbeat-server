package com.nextdv.infrastructure.healthcheck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckLogJpaRepository extends JpaRepository<HealthCheckLogEntity, UUID> {

  List<HealthCheckLogEntity> findAllByPlatformId(UUID platformId);

  Optional<HealthCheckLogEntity> findTopByPlatformIdOrderByCheckedAtDesc(UUID platformId);
}
