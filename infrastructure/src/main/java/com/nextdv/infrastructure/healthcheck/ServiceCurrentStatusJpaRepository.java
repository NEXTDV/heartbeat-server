package com.nextdv.infrastructure.healthcheck;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface ServiceCurrentStatusJpaRepository
    extends
      JpaRepository<ServiceCurrentStatusEntity, UUID> {
}
