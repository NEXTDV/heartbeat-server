package com.nextdv.api.account;

import com.nextdv.api.common.CommonResponse;
import com.nextdv.domain.account.Account;
import com.nextdv.domain.account.AccountService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<List<AccountResponse>>> list() {
    List<Account> accounts = accountService.findAll();
    List<AccountResponse> data = AccountMapper.toResponseList(accounts);
    return ResponseEntity.ok(CommonResponse.ok(data));
  }
}
