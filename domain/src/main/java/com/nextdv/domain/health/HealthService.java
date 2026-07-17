package com.nextdv.domain.health;

import org.springframework.stereotype.Service;

@Service
public class HealthService {

  public HealthResult check() {
    return new HealthResult("ok");
  }
}
