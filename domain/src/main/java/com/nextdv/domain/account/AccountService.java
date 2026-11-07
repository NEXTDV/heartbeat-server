package com.nextdv.domain.account;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 클래스명: AccountService
 * 작성자: JBumLee
 *
 * 계정 생성 및 조회 비즈니스 로직을 담당하는 서비스
 */
@Slf4j
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
    List<Account> accounts = accountRepository.findAll();
    log.info(
        "계정 전체 조회 — 건수: {}",
        accounts.size()
    );
    return accounts;
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
      log.warn("계정 생성 실패 — 이메일 누락");
      throw new IllegalArgumentException("이메일은 필수입니다.");
    }
    if (accountRepository.existsByEmail(email)) {
      log.warn(
          "계정 생성 실패 — 중복 이메일: {}",
          email
      );
      throw new IllegalStateException("이미 사용 중인 이메일입니다.");
    }
    Account account = accountRepository.save(new Account(UUID.randomUUID(), email));
    log.info(
        "계정 생성 — id: {}, 이메일: {}",
        account.getId(),
        email
    );
    return account;
  }
}
