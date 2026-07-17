package com.nextdv.domain.platform;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformService {

  private final PlatformRepository platformRepository;

  public List<Platform> findAll() {
    return platformRepository.findAll();
  }

  public Platform register(
      String name,
      ServiceCategory category,
      String healthCheckUrl,
      int timeoutMs,
      int degradedThresholdMs,
      String iconUrl) {
    Platform platform = new Platform(
        UUID.randomUUID(),
        name,
        category,
        healthCheckUrl,
        timeoutMs,
        degradedThresholdMs,
        iconUrl,
        true
    );
    return platformRepository.save(platform);
  }
}
