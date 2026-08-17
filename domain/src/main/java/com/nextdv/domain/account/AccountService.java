package com.nextdv.domain.account;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  public Account create(String email) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("이메일은 필수입니다.");
    }
    if (accountRepository.existsByEmail(email)) {
      throw new IllegalStateException("이미 사용 중인 이메일입니다.");
    }
    return accountRepository.save(new Account(UUID.randomUUID(), email));
  }
}
