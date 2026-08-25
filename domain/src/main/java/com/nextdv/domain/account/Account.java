package com.nextdv.domain.account;

import java.util.UUID;

/**
 * 클래스명: Account
 * 작성자: JBumLee
 *
 * 사용자 계정 도메인 객체
 */
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
