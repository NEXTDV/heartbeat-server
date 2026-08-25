package com.nextdv.domain.account;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 클래스명: AccountService
 * 작성자: JBumLee
 *
 * 계정 생성 및 조회 비즈니스 로직을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  /**
   * 메소드이름: findAll
   * 모든 계정을 조회한다
   *
   * @return 전체 계정 목록
   */
  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  /**
   * 메소드이름: create
   * 이메일 유효성 검증 후 신규 계정을 생성한다
   *
   * @param email 생성할 계정의 이메일 주소
   * @return 생성된 계정 객체
   */
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
