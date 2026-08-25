package com.nextdv.api.account;

import java.util.UUID;

/**
 * 클래스명: AccountResponse
 * 작성자: JBumLee
 *
 * 계정 조회 응답 DTO
 */
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
