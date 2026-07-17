package com.nextdv.api.health;

public class HealthResponse {

  private final String status;

  private HealthResponse(String status) {
    this.status = status;
  }

  public static HealthResponse ok() {
    return new HealthResponse("ok");
  }

  public String getStatus() {
    return status;
  }
}
