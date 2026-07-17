package com.nextdv.domain.health;

public class HealthResult {

  private final String status;

  public HealthResult(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
