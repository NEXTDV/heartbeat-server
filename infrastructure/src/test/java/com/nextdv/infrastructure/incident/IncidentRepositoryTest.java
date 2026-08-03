package com.nextdv.infrastructure.incident;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.common.UuidV7;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.incident.Incident;
import com.nextdv.domain.incident.IncidentRepository;
import com.nextdv.domain.incident.IncidentStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(IncidentRepositoryImpl.class)
@Testcontainers
class IncidentRepositoryTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private IncidentRepository incidentRepository;

  @Test
  void 에러_상태에서_Incident가_OPEN으로_저장된다() {
    UUID platformId = UUID.randomUUID();
    Incident incident = new Incident(
        UuidV7.generate(), platformId, ServiceStatus.MAJOR_OUTAGE,
        IncidentStatus.OPEN, Instant.now(), null
    );

    incidentRepository.save(incident);

    Optional<Incident> found = incidentRepository.findOpenByPlatformId(platformId);
    assertThat(found).isPresent();
    assertThat(found.get().getStatus()).isEqualTo(IncidentStatus.OPEN);
    assertThat(found.get().getImpact()).isEqualTo(ServiceStatus.MAJOR_OUTAGE);
    assertThat(found.get().getResolvedAt()).isNull();
  }

  @Test
  void 복구_시_RESOLVED로_업데이트된다() {
    UUID platformId = UUID.randomUUID();
    UUID id = UuidV7.generate();
    Instant startedAt = Instant.now();
    incidentRepository.save(
        new Incident(
            id, platformId, ServiceStatus.PARTIAL_OUTAGE,
            IncidentStatus.OPEN, startedAt, null
        )
    );

    Incident resolved = new Incident(
        id, platformId, ServiceStatus.PARTIAL_OUTAGE,
        IncidentStatus.RESOLVED, startedAt, Instant.now()
    );
    incidentRepository.save(resolved);

    Optional<Incident> open = incidentRepository.findOpenByPlatformId(platformId);
    assertThat(open).isEmpty();
  }

  @Test
  void 전체_인시던트를_최신순으로_조회한다() {
    incidentRepository.save(
        new Incident(
            UuidV7.generate(), UUID.randomUUID(), ServiceStatus.MAJOR_OUTAGE,
            IncidentStatus.RESOLVED, Instant.now().minusSeconds(120), Instant.now()
        )
    );
    incidentRepository.save(
        new Incident(
            UuidV7.generate(), UUID.randomUUID(), ServiceStatus.PARTIAL_OUTAGE,
            IncidentStatus.OPEN, Instant.now(), null
        )
    );

    List<Incident> all = incidentRepository.findAll();

    assertThat(all).hasSize(2);
    assertThat(all.get(0).getStatus()).isEqualTo(IncidentStatus.OPEN);
  }

  @Test
  void ID로_단건_조회한다() {
    UUID id = UuidV7.generate();
    UUID platformId = UUID.randomUUID();
    incidentRepository.save(
        new Incident(
            id, platformId, ServiceStatus.MAJOR_OUTAGE,
            IncidentStatus.OPEN, Instant.now(), null
        )
    );

    Optional<Incident> found = incidentRepository.findById(id);

    assertThat(found).isPresent();
    assertThat(found.get().getPlatformId()).isEqualTo(platformId);
  }
}
