package com.nextdv.infrastructure.platform;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformJpaRepository
    extends
      JpaRepository<PlatformEntity, UUID> {
}
