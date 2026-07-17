package com.nextdv.domain.account;

import java.util.UUID;

public class Account {

  private final UUID id;
  private final String email;

  public Account(UUID id, String email) {
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
