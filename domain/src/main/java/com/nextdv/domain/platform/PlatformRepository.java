package com.nextdv.domain.platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlatformRepository {

  List<Platform> findAll(boolean activeOnly);

  Optional<Platform> findById(UUID id);
}
