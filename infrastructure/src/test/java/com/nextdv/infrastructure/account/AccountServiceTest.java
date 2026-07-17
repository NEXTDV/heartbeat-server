package com.nextdv.infrastructure.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.account.Account;
import com.nextdv.domain.account.AccountService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({AccountRepositoryImpl.class, AccountService.class})
@Testcontainers
class AccountServiceTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private AccountJpaRepository jpaRepository;

  @Autowired
  private AccountService accountService;

  @Test
  void 저장된_계정을_전체_조회한다() {
    UUID id = UUID.randomUUID();
    jpaRepository.save(new AccountEntity(id, "user@nextdv.com"));

    List<Account> result = accountService.findAll();

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(id);
    assertThat(result.get(0).getEmail()).isEqualTo("user@nextdv.com");
  }

  @Test
  void 계정이_없으면_빈_목록을_반환한다() {
    List<Account> result = accountService.findAll();

    assertThat(result).isEmpty();
  }
}
