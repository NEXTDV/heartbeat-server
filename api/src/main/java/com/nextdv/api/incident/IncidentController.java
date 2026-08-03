package com.nextdv.api.incident;

import com.nextdv.api.common.ApiResponse;
import com.nextdv.domain.incident.Incident;
import com.nextdv.domain.incident.IncidentRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
public class IncidentController {

  private final IncidentRepository incidentRepository;

  @GetMapping
  public ResponseEntity<ApiResponse<List<IncidentResponse>>> list() {
    List<Incident> incidents = incidentRepository.findAll();
    return ResponseEntity.ok(ApiResponse.ok(IncidentMapper.toResponseList(incidents)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<IncidentResponse>> findById(@PathVariable UUID id) {
    Incident incident = incidentRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("인시던트를 찾을 수 없습니다."));
    return ResponseEntity.ok(ApiResponse.ok(IncidentMapper.toResponse(incident)));
  }
}
