package com.nextdv.infrastructure.platform;

import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlatformRepositoryImpl implements PlatformRepository {

  private final PlatformJpaRepository platformJpaRepository;

  @Override
  public List<Platform> findAll() {
    return platformJpaRepository.findAll().stream().map(this::toDomain)
        .toList();
  }

  @Override
  public Platform save(Platform platform) {
    PlatformEntity saved = platformJpaRepository.save(toEntity(platform));
    return toDomain(saved);
  }

  private Platform toDomain(PlatformEntity entity) {
    return new Platform(
        entity.getId(),
        entity.getName(),
        entity.getCategory(),
        entity.getHealthCheckUrl(),
        entity.getTimeoutMs(),
        entity.getDegradedThresholdMs(),
        entity.getIconUrl(),
        entity.isActive()
    );
  }

  private PlatformEntity toEntity(Platform platform) {
    return new PlatformEntity(
        platform.getId(),
        platform.getName(),
        platform.getCategory(),
        platform.getHealthCheckUrl(),
        platform.getTimeoutMs(),
        platform.getDegradedThresholdMs(),
        platform.getIconUrl(),
        platform.isActive()
    );
  }
}
