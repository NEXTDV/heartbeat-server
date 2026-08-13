package com.nextdv.infrastructure.healthcheck;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.common.UuidV7;
import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogService;
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
@Import({HealthCheckLogRepositoryImpl.class, HealthCheckLogService.class})
@Testcontainers
class HealthCheckLogServiceTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private HealthCheckLogJpaRepository jpaRepository;

  @Autowired
  private HealthCheckLogService healthCheckLogService;

  @Test
  void 로그가_없으면_빈_Optional을_반환한다() {
    Optional<HealthCheckLog> result = healthCheckLogService.findLatestByPlatformId(
        UUID.randomUUID()
    );

    assertThat(result).isEmpty();
  }

  @Test
  void 최신_로그를_반환한다() {
    UUID platformId = UUID.randomUUID();
    Instant older = Instant.now().minusSeconds(60);
    Instant newer = Instant.now();
    jpaRepository.save(
        new HealthCheckLogEntity(
            UuidV7.generate(), platformId, ServiceStatusEntity.MAJOR_OUTAGE, null, 500, older
        )
    );
    jpaRepository.save(
        new HealthCheckLogEntity(
            UuidV7.generate(), platformId, ServiceStatusEntity.OPERATIONAL, null, 200, newer
        )
    );

    Optional<HealthCheckLog> result = healthCheckLogService.findLatestByPlatformId(platformId);

    assertThat(result).isPresent();
    assertThat(result.get().getStatus()).isEqualTo(ServiceStatus.OPERATIONAL);
    assertThat(result.get().getCheckedAt()).isEqualTo(newer);
  }

  @Test
  void 로그_목록이_없으면_빈_리스트를_반환한다() {
    List<HealthCheckLog> result = healthCheckLogService.findAllByPlatformId(UUID.randomUUID());

    assertThat(result).isEmpty();
  }

  @Test
  void platformId로_로그_목록을_조회한다() {
    UUID platformId = UUID.randomUUID();
    Instant now = Instant.now();
    jpaRepository.save(
        new HealthCheckLogEntity(
            UuidV7.generate(), platformId, ServiceStatusEntity.OPERATIONAL, null, 200, now
        )
    );
    jpaRepository.save(
        new HealthCheckLogEntity(
            UuidV7.generate(), platformId, ServiceStatusEntity.DEGRADED, null, 1500, now
        )
    );

    List<HealthCheckLog> result = healthCheckLogService.findAllByPlatformId(platformId);

    assertThat(result).hasSize(2);
    assertThat(result).extracting(HealthCheckLog::getPlatformId).containsOnly(platformId);
  }

  @Test
  void 다른_플랫폼_로그는_조회되지_않는다() {
    UUID platformId = UUID.randomUUID();
    UUID otherPlatformId = UUID.randomUUID();
    Instant now = Instant.now();
    jpaRepository.save(
        new HealthCheckLogEntity(
            UuidV7.generate(), platformId, ServiceStatusEntity.OPERATIONAL, null, 100, now
        )
    );
    jpaRepository.save(
        new HealthCheckLogEntity(
            UuidV7.generate(), otherPlatformId, ServiceStatusEntity.MAJOR_OUTAGE, null, 100, now
        )
    );

    List<HealthCheckLog> result = healthCheckLogService.findAllByPlatformId(platformId);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getStatus()).isEqualTo(ServiceStatus.OPERATIONAL);
  }
}
