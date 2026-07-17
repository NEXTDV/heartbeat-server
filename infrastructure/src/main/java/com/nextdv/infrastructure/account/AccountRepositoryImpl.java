package com.nextdv.infrastructure.account;

import com.nextdv.domain.account.Account;
import com.nextdv.domain.account.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

  private final AccountJpaRepository accountJpaRepository;

  @Override
  public List<Account> findAll() {
    return accountJpaRepository.findAll().stream()
        .map(entity -> new Account(entity.getId(), entity.getEmail()))
        .toList();
  }
}
