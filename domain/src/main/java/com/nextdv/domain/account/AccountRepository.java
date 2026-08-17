package com.nextdv.domain.account;

import java.util.List;

public interface AccountRepository {

  List<Account> findAll();

  Account save(Account account);

  boolean existsByEmail(String email);
}
