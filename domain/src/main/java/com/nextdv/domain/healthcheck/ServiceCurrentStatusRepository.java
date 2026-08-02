package com.nextdv.domain.healthcheck;

import java.util.Optional;
import java.util.UUID;

public interface ServiceCurrentStatusRepository {

  void save(ServiceCurrentStatus status);

  Optional<ServiceCurrentStatus> findByPlatformId(UUID platformId);
}
