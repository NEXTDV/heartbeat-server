package com.nextdv.infrastructure.platform;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformService;
import com.nextdv.domain.platform.ServiceCategory;
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
  void 플랫폼이_없으면_빈_목록을_반환한다() {
    List<Platform> result = platformService.findAll();

    assertThat(result).isEmpty();
  }

  @Test
  void 등록된_플랫폼을_전체_조회한다() {
    jpaRepository.save(
        new PlatformEntity(
            UUID.randomUUID(), "GitHub", ServiceCategory.DEVTOOL,
            "https://api.github.com", 3000, 1000, null, true
        )
    );
    jpaRepository.save(
        new PlatformEntity(
            UUID.randomUUID(), "AWS", ServiceCategory.CLOUD,
            "https://health.aws.amazon.com", 3000, 1000, null, true
        )
    );

    List<Platform> result = platformService.findAll();

    assertThat(result).hasSize(2);
    assertThat(result).extracting(Platform::getName)
        .containsExactlyInAnyOrder(
            "GitHub",
            "AWS"
        );
  }

  @Test
  void ID로_플랫폼을_단건_조회한다() {
    UUID id = UUID.randomUUID();
    jpaRepository.save(
        new PlatformEntity(
            id, "GitHub", ServiceCategory.DEVTOOL,
            "https://api.github.com", 3000, 1000, null, true
        )
    );

    Optional<Platform> result = platformService.findById(id);

    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
    assertThat(result.get().getName()).isEqualTo("GitHub");
  }

  @Test
  void 존재하지_않는_ID로_조회하면_빈_Optional을_반환한다() {
    Optional<Platform> result = platformService.findById(UUID.randomUUID());

    assertThat(result).isEmpty();
  }
}
