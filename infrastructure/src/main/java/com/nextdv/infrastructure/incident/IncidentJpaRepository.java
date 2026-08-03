package com.nextdv.infrastructure.incident;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface IncidentJpaRepository extends JpaRepository<IncidentEntity, UUID> {

  Optional<IncidentEntity> findByPlatformIdAndStatus(UUID platformId, IncidentStatusEntity status);

  List<IncidentEntity> findAllByOrderByStartedAtDesc();
}
