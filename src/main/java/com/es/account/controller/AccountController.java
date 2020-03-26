package com.es.account.controller;


import com.es.account.entity.Account;
import com.es.account.service.AccountService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(name = "AccountController", description = "账户控制器")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiMethod(description = "获取用户账户信息")
    @GetMapping("/accounts/{userId}")
    public ResponseEntity<Account> getAccount(
            @ApiQueryParam(name = "userId", description = "用户id")
            @PathVariable Long userId,
            @ApiQueryParam(name = "accountType", description = "账户类型")
            @RequestParam Account.AccountType accountType) {
        return ResponseEntity.ok(accountService.getAccount(userId, accountType));
    }


    @ApiMethod(description = "获取账户列表")
    @GetMapping("/accounts/{page}")
    public ResponseEntity<Page<Account>> getAccounts(
            @ApiQueryParam(name = "accountType", description = "账户类型")
            @RequestParam Account.AccountType accountType,
            @ApiQueryParam(name = "page", description = "分页页码")
            @PathVariable Integer page) {
        return ResponseEntity.ok(accountService.getAccounts(accountType, page));
    }

}
