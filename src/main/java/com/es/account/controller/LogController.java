package com.es.account.controller;


import lombok.extern.slf4j.Slf4j;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(name = "LogController", description = "日志触发")
public class LogController {

    @ApiMethod(description = "获取用户账户信息")
    @GetMapping("/logs")
    public ResponseEntity<String> logs() {
        for (int i = 0; i < 10; i++) {
            log.info("this is -->" + i);
        }
        return ResponseEntity.ok("已发送～");
    }
}
