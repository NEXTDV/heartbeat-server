package com.nextdv.infrastructure.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 * class: AccountEntity 작성자: JBumLee
 *
 * accounts 테이블과 매핑되는 JPA 엔티티
 */
@Entity
@Table(name = "accounts")
public class AccountEntity {

  @Id
  private UUID id;

  @Column(nullable = false, unique = true)
  private String email;

  protected AccountEntity() {
  }

  public AccountEntity(UUID id, String email) {
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
