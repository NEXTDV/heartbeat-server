package com.nextdv.infrastructure.demo;

import com.nextdv.domain.platform.ServiceCategory;
import com.nextdv.infrastructure.platform.PlatformEntity;
import com.nextdv.infrastructure.platform.PlatformJpaRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoPlatformSeeder implements ApplicationRunner {

  private static final UUID DEMO_ID = UUID.fromString("00000000-0000-0000-0000-000000000099");

  private final PlatformJpaRepository platformJpaRepository;

  @Override
  public void run(ApplicationArguments args) {
    platformJpaRepository.save(
        new PlatformEntity(
            DEMO_ID,
            "Test서버",
            ServiceCategory.OTHER,
            "demo://test",
            10000,
            1000,
            "https://avatars.githubusercontent.com/u/202518705?s=200&v=4",
            true
        )
    );
  }
}
