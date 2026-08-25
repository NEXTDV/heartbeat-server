package com.nextdv.domain.account;

import java.util.List;

/**
 * 클래스명: AccountRepository
 * 작성자: JBumLee
 *
 * 계정 도메인 저장소 인터페이스
 */
public interface AccountRepository {

  /**
   * 메소드이름: findAll
   * 모든 계정을 조회한다
   *
   * @return 전체 계정 목록
   */
  List<Account> findAll();

  /**
   * 메소드이름: save
   * 계정을 저장한다
   *
   * @param account 저장할 계정 객체
   * @return 저장된 계정 객체
   */
  Account save(Account account);

  /**
   * 메소드이름: existsByEmail
   * 해당 이메일로 가입된 계정이 존재하는지 확인한다
   *
   * @param email 중복 확인할 이메일 주소
   * @return 이메일 존재 여부
   */
  boolean existsByEmail(String email);
}
