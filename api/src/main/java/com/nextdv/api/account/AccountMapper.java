package com.nextdv.api.account;

import com.nextdv.domain.account.Account;
import java.util.List;

public class AccountMapper {

  private AccountMapper() {
  }

  public static AccountResponse toResponse(Account account) {
    return new AccountResponse(account.getId(), account.getEmail());
  }

  public static List<AccountResponse> toResponseList(
      List<Account> accounts) {
    return accounts.stream().map(AccountMapper::toResponse).toList();
  }
}
