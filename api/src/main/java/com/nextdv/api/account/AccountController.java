package com.nextdv.api.account;

import com.nextdv.api.common.CommonResponse;
import com.nextdv.domain.account.Account;
import com.nextdv.domain.account.AccountService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 클래스명: AccountController
 * 작성자: JBumLee
 *
 * 계정 관련 REST API 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  /**
   * 메소드이름: list
   * 등록된 모든 계정을 조회한다
   *
   * @return 전체 계정 목록 응답
   */
  @GetMapping
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<List<AccountResponse>>> list() {
    List<Account> accounts = accountService.findAll();
    List<AccountResponse> data = AccountMapper.toResponseList(accounts);
    return ResponseEntity.ok(CommonResponse.ok(data));
  }

  /**
   * 메소드이름: create
   * 이메일로 신규 계정을 생성한다
   *
   * @param request 계정 생성 요청 (이메일 포함)
   * @return 생성된 계정 응답
   */
  @PostMapping
  @ApiResponse(responseCode = "400", description = "이메일 필드 누락 또는 이메일 형식 오류")
  @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<AccountResponse>> create(
      @Valid @RequestBody AccountRequest request) {
    Account account = accountService.create(request.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CommonResponse.ok(AccountMapper.toResponse(account)));
  }
}
