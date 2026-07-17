package com.nextdv.api.account;

import java.util.UUID;

public class AccountResponse {

  private final UUID id;
  private final String email;

  public AccountResponse(UUID id, String email) {
    this.id = id;
    this.email = email;
  }

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
}
