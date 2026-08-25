package com.nextdv.api.account;

import com.nextdv.domain.account.Account;
import java.util.List;

/**
 * 클래스명: AccountMapper
 * 작성자: JBumLee
 *
 * Account 도메인 객체를 AccountResponse DTO로 변환하는 매퍼
 */
public class AccountMapper {

  private AccountMapper() {
  }

  /**
   * 메소드이름: toResponse
   * Account 도메인 객체를 응답 DTO로 변환한다
   *
   * @param account 변환할 계정 도메인 객체
   * @return AccountResponse DTO
   */
  public static AccountResponse toResponse(Account account) {
    return new AccountResponse(account.getId(), account.getEmail());
  }

  /**
   * 메소드이름: toResponseList
   * Account 도메인 객체 목록을 응답 DTO 목록으로 변환한다
   *
   * @param accounts 변환할 계정 도메인 객체 목록
   * @return AccountResponse DTO 목록
   */
  public static List<AccountResponse> toResponseList(
      List<Account> accounts) {
    return accounts.stream().map(AccountMapper::toResponse).toList();
  }
}
