package com.nextdv.infrastructure.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.infrastructure.platform.PlatformEntity;
import com.nextdv.infrastructure.platform.PlatformJpaRepository;
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
@Import(DemoPlatformSeeder.class)
@Testcontainers
class DemoPlatformSeederTest {

  private static final UUID DEMO_ID = UUID.fromString("00000000-0000-0000-0000-000000000099");

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private PlatformJpaRepository platformJpaRepository;

  @Autowired
  private DemoPlatformSeeder seeder;

  @Test
  void 서버_시작_시_Test서버가_DB에_저장된다() throws Exception {
    seeder.run(null);

    Optional<PlatformEntity> result = platformJpaRepository.findById(DEMO_ID);
    assertThat(result).isPresent();
    assertThat(result.get().getName()).isEqualTo("Test서버");
  }

  @Test
  void 중복_실행해도_Test서버는_하나만_존재한다() throws Exception {
    seeder.run(null);
    seeder.run(null);

    long count = platformJpaRepository.findAll().stream()
        .filter(p -> p.getId().equals(DEMO_ID))
        .count();
    assertThat(count).isEqualTo(1);
  }
}
