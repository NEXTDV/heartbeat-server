package com.nextdv.infrastructure.platform;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlatformDataInitializer implements ApplicationRunner {

  private final PlatformJpaRepository platformJpaRepository;
  private final PlatformProperties platformProperties;

  @Override
  public void run(ApplicationArguments args) {
    Set<String> existingNames = platformJpaRepository.findAll().stream()
        .map(PlatformEntity::getName)
        .collect(Collectors.toSet());

    platformProperties.platforms().stream()
        .filter(config -> !existingNames.contains(config.name()))
        .map(this::toEntity)
        .forEach(platformJpaRepository::save);
  }

  private PlatformEntity toEntity(PlatformProperties.PlatformConfig config) {
    return new PlatformEntity(
        UUID.randomUUID(),
        config.name(),
        config.category(),
        config.healthCheckUrl(),
        config.timeoutMs(),
        config.degradedThresholdMs(),
        config.iconUrl(),
        true
    );
  }
}
