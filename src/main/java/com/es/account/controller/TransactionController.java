package com.es.account.controller;


import com.es.account.entity.Account;
import com.es.account.entity.Transaction;
import com.es.account.service.TransactionService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(name = "TransactionController", description = "交易控制器")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @ApiMethod(description = "创建交易记录")
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> cashTransaction(
            @ApiQueryParam(name = "transaction", description = "交易信息")
            @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.createCashTransaction(transaction));
    }

    @ApiMethod(description = "积分兑换")
    @PostMapping("/transactions/points")
    public ResponseEntity<Transaction> pointsTransaction(
            @ApiQueryParam(name = "transaction", description = "交易信息")
            @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.createExchangeTransaction(transaction));
    }

    @ApiMethod(description = "更新用户交易记录")
    @PutMapping("/transactions")
    public ResponseEntity<Transaction> updateTransaction(
            @ApiQueryParam(name = "transaction", description = "交易信息")
            @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.updateTransaction(transaction));
    }

    @ApiMethod(description = "获取用户交易记录")
    @GetMapping("/transactions")
    public ResponseEntity<Page<Transaction>> getTransactions(
            @ApiQueryParam(name = "userId", description = "id")
            @RequestParam long userId,
            @ApiQueryParam(name = "accountType", description = "账户类型")
            @RequestParam Account.AccountType accountType,
            @ApiQueryParam(name = "transType", description = "交易类型")
            @RequestParam Transaction.TransType transactionType,
            @ApiQueryParam(name = "status", description = "交易状态")
            @RequestParam Transaction.STATUS status,
            @ApiQueryParam(name = "page", description = "分页参数")
            @RequestParam int page) {
        return ResponseEntity.ok(transactionService.getPagedTransactions(userId, accountType, transactionType, status, page));
    }

}
