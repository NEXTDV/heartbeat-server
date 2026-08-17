package com.nextdv.infrastructure.account;

import com.nextdv.domain.account.Account;
import com.nextdv.domain.account.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

  @Override
  public Account save(Account account) {
    try {
      AccountEntity saved = accountJpaRepository
          .save(new AccountEntity(account.getId(), account.getEmail()));
      return new Account(saved.getId(), saved.getEmail());
    } catch (DataIntegrityViolationException e) {
      throw new IllegalStateException("이미 사용 중인 이메일입니다.");
    }
  }

  @Override
  public boolean existsByEmail(String email) {
    return accountJpaRepository.existsByEmail(email);
  }
}
