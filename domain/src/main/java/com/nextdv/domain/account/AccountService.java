package com.nextdv.domain.account;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  public List<Account> findAll() {
    return accountRepository.findAll();
  }
}
