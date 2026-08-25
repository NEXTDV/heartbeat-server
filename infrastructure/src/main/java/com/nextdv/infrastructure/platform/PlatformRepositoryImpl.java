package com.nextdv.infrastructure.platform;

import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 클래스명: PlatformRepositoryImpl
 * 작성자: JBumLee
 *
 * PlatformRepository 인터페이스의 JPA 기반 구현체
 */
@Repository
@RequiredArgsConstructor
public class PlatformRepositoryImpl implements PlatformRepository {

  private final PlatformJpaRepository platformJpaRepository;

  @Override
  public List<Platform> findAll(boolean activeOnly) {
    return platformJpaRepository.findAll().stream()
        .filter(entity -> !activeOnly || entity.isActive())
        .map(this::toDomain)
        .toList();
  }

  @Override
  public Optional<Platform> findById(UUID id) {
    return platformJpaRepository.findById(id).map(this::toDomain);
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

}
