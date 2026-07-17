package com.nextdv.domain.account;

import java.util.List;

public interface AccountRepository {

  List<Account> findAll();
}
