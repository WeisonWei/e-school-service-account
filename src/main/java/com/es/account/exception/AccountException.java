package com.es.account.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class AccountException extends RuntimeException {

    private Integer code;

    public AccountException() {
        super();
    }

    public AccountException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AccountException(Throwable cause) {
        super(cause);
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

}
