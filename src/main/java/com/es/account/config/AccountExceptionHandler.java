package com.es.account.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountException.class)
    HttpServletResponse handleControllerException(HttpServletResponse response, AccountException ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return response;
    }

}