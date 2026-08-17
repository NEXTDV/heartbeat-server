package com.nextdv.api.account;

import com.nextdv.api.common.ApiResponse;
import com.nextdv.domain.account.Account;
import com.nextdv.domain.account.AccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<AccountResponse>>> list() {
    List<Account> accounts = accountService.findAll();
    List<AccountResponse> data = AccountMapper.toResponseList(accounts);
    return ResponseEntity.ok(ApiResponse.ok(data));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<AccountResponse>> create(@RequestBody AccountRequest request) {
    Account account = accountService.create(request.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.ok(AccountMapper.toResponse(account)));
  }
}
