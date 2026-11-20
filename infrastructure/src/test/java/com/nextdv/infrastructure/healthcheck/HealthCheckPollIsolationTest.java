package com.nextdv.infrastructure.healthcheck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.nextdv.domain.channel.ChannelRepository;
import com.nextdv.domain.healthcheck.HealthCheckLogRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.PlatformService;
import com.nextdv.domain.platform.ServiceCategory;
import com.nextdv.infrastructure.channel.ChannelRepositoryImpl;
import com.nextdv.infrastructure.notification.FakeDiscordSender;
import com.nextdv.infrastructure.notification.FakeEmailSender;
import com.nextdv.infrastructure.notification.FakeSlackSender;
import com.nextdv.infrastructure.platform.PlatformEntity;
import com.nextdv.infrastructure.platform.PlatformJpaRepository;
import com.nextdv.infrastructure.platform.PlatformRepositoryImpl;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({
    PlatformRepositoryImpl.class,
    PlatformService.class,
    HealthCheckLogRepositoryImpl.class,
    ChannelRepositoryImpl.class
})
@Testcontainers
class HealthCheckPollIsolationTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private PlatformJpaRepository platformJpaRepository;

  @Autowired
  private PlatformService platformService;

  @Autowired
  private HealthCheckLogRepository healthCheckLogRepository;

  @Autowired
  private ChannelRepository channelRepository;

  private HealthCheckPollService service;

  @BeforeEach
  void setUp() {
    service = new HealthCheckPollService(
        platformService, healthCheckLogRepository, RestClient.create(), channelRepository,
        new FakeEmailSender(), new FakeSlackSender(), new FakeDiscordSender()
    );
  }

  @Test
  void 한_플랫폼_폴링이_실패해도_나머지_플랫폼은_폴링된다() {
    UUID brokenId = UUID.randomUUID();
    UUID healthyId = UUID.randomUUID();
    platformJpaRepository.save(
        new PlatformEntity(
            brokenId, "Broken", ServiceCategory.OTHER,
            "not-a-valid-url", 3000, 1000, null, true
        )
    );
    platformJpaRepository.save(
        new PlatformEntity(
            healthyId, "Unreachable", ServiceCategory.OTHER,
            "http://localhost:1", 3000, 1000, null, true
        )
    );

    assertThatCode(service::pollAll).doesNotThrowAnyException();

    assertThat(healthCheckLogRepository.findLatestByPlatformId(healthyId))
        .hasValueSatisfying(
            log -> assertThat(log.getStatus()).isEqualTo(ServiceStatus.MAJOR_OUTAGE)
        );
    assertThat(healthCheckLogRepository.findLatestByPlatformId(brokenId)).isEmpty();
  }
}
