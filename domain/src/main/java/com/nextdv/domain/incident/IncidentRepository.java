package com.nextdv.domain.incident;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncidentRepository {

  Incident save(Incident incident);

  Optional<Incident> findOpenByPlatformId(UUID platformId);

  List<Incident> findAll();

  Optional<Incident> findById(UUID id);
}
