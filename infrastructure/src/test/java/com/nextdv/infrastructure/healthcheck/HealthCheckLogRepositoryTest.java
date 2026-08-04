package com.nextdv.infrastructure.healthcheck;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.common.UuidV7;
import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
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
@Import(HealthCheckLogRepositoryImpl.class)
@Testcontainers
class HealthCheckLogRepositoryTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private HealthCheckLogRepository healthCheckLogRepository;

  @Test
  void 헬스체크_로그를_저장하고_platformId로_조회한다() {
    UUID platformId = UUID.randomUUID();
    healthCheckLogRepository.save(
        new HealthCheckLog(
            UuidV7.generate(), platformId, ServiceStatus.OPERATIONAL, 200L,
            Instant.now()
        )
    );
    healthCheckLogRepository.save(
        new HealthCheckLog(
            UuidV7.generate(), platformId, ServiceStatus.DEGRADED, 1500L,
            Instant.now()
        )
    );

    List<HealthCheckLog> logs = healthCheckLogRepository.findAllByPlatformId(platformId);

    assertThat(logs).hasSize(2);
    assertThat(logs).extracting(HealthCheckLog::getPlatformId)
        .allMatch(id -> id.equals(platformId));
  }

  @Test
  void 다른_플랫폼_로그는_조회되지_않는다() {
    UUID platformId = UUID.randomUUID();
    UUID otherPlatformId = UUID.randomUUID();
    healthCheckLogRepository.save(
        new HealthCheckLog(
            UuidV7.generate(), platformId, ServiceStatus.OPERATIONAL, 100L,
            Instant.now()
        )
    );
    healthCheckLogRepository.save(
        new HealthCheckLog(
            UuidV7.generate(), otherPlatformId, ServiceStatus.MAJOR_OUTAGE, 100L,
            Instant.now()
        )
    );

    List<HealthCheckLog> logs = healthCheckLogRepository.findAllByPlatformId(platformId);

    assertThat(logs).hasSize(1);
    assertThat(logs.get(0).getStatus()).isEqualTo(ServiceStatus.OPERATIONAL);
  }

  @Test
  void 가장_최신_로그를_단건_조회한다() {
    UUID platformId = UUID.randomUUID();
    Instant older = Instant.now().minusSeconds(60);
    Instant newer = Instant.now();
    healthCheckLogRepository.save(
        new HealthCheckLog(UuidV7.generate(), platformId, ServiceStatus.MAJOR_OUTAGE, 500L, older)
    );
    healthCheckLogRepository.save(
        new HealthCheckLog(UuidV7.generate(), platformId, ServiceStatus.OPERATIONAL, 200L, newer)
    );

    Optional<HealthCheckLog> latest = healthCheckLogRepository.findLatestByPlatformId(platformId);

    assertThat(latest).isPresent();
    assertThat(latest.get().getStatus()).isEqualTo(ServiceStatus.OPERATIONAL);
    assertThat(latest.get().getCheckedAt()).isEqualTo(newer);
  }

  @Test
  void 존재하지_않는_platformId는_빈_Optional을_반환한다() {
    Optional<HealthCheckLog> latest = healthCheckLogRepository
        .findLatestByPlatformId(UUID.randomUUID());

    assertThat(latest).isEmpty();
  }
}
