package com.nextdv.infrastructure.platform;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformService;
import com.nextdv.domain.platform.ServiceCategory;
import java.util.List;
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
@Import({PlatformRepositoryImpl.class, PlatformService.class})
@Testcontainers
class PlatformServiceTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      "postgres:16-alpine"
  );

  @Autowired
  private PlatformJpaRepository jpaRepository;

  @Autowired
  private PlatformService platformService;

  @Test
  void 플랫폼을_등록한다() {
    Platform result = platformService.register(
        "GitHub",
        ServiceCategory.DEVTOOL,
        "https://api.github.com",
        3000,
        1000,
        null
    );

    assertThat(result.getId()).isNotNull();
    assertThat(result.getName()).isEqualTo("GitHub");
    assertThat(result.getCategory()).isEqualTo(ServiceCategory.DEVTOOL);
    assertThat(result.getHealthCheckUrl()).isEqualTo("https://api.github.com");
    assertThat(result.isActive()).isTrue();
  }

  @Test
  void 등록된_플랫폼을_전체_조회한다() {
    platformService.register(
        "GitHub",
        ServiceCategory.DEVTOOL,
        "https://api.github.com",
        3000,
        1000,
        null
    );
    platformService.register(
        "AWS",
        ServiceCategory.CLOUD,
        "https://health.aws.amazon.com",
        3000,
        1000,
        null
    );

    List<Platform> result = platformService.findAll();

    assertThat(result).hasSize(2);
    assertThat(result).extracting(Platform::getName).containsExactlyInAnyOrder(
        "GitHub",
        "AWS"
    );
  }
}
