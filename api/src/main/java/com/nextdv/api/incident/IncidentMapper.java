package com.nextdv.api.incident;

import com.nextdv.domain.incident.Incident;
import java.util.List;

public class IncidentMapper {

  private IncidentMapper() {
  }

  public static IncidentResponse toResponse(Incident incident) {
    return new IncidentResponse(
        incident.getId(),
        incident.getPlatformId(),
        incident.getImpact(),
        incident.getStatus(),
        incident.getStartedAt(),
        incident.getResolvedAt()
    );
  }

  public static List<IncidentResponse> toResponseList(List<Incident> incidents) {
    return incidents.stream()
        .map(IncidentMapper::toResponse)
        .toList();
  }
}
