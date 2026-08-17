package com.nextdv.api.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AccountRequest {

  @NotBlank(message = "이메일은 필수입니다.")
  @Email(message = "올바른 이메일 형식이어야 합니다.")
  private String email;

  public AccountRequest() {
  }

  public String getEmail() {
    return email;
  }
}
