package com.nextdv.domain.platform;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformService {

  private final PlatformRepository platformRepository;

  public List<Platform> findAll() {
    return platformRepository.findAll(true);
  }
}
