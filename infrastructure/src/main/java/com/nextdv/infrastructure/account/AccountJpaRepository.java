package com.nextdv.infrastructure.account;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 클래스명: AccountJpaRepository
 * 작성자: JBumLee
 *
 * accounts 테이블 JPA 저장소 인터페이스
 */
public interface AccountJpaRepository
    extends
      JpaRepository<AccountEntity, UUID> {

  boolean existsByEmail(String email);
}
