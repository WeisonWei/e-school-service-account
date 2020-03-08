package com.es.account.config;

import com.es.account.exception.AccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class StudentExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountException.class)
    ResponseEntity<AccountException> handleControllerException(HttpServletResponse response, AccountException ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();
        ResponseEntity<AccountException> body = bodyBuilder.body(ex);
        log.error("Account Service Exception: Code[{}] Message[{}]", ex.getCode(), ex.getMessage());
        return body;
    }

}