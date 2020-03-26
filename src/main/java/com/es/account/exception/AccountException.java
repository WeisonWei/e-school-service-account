package com.es.account.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Builder
@EqualsAndHashCode(callSuper = true)
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
