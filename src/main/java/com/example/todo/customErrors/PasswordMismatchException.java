package com.example.todo.customErrors;

import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends ServerException {
    private static final String DEFAULT_MESSAGE = "패스워드가 일치하지 않습니다.";

    public PasswordMismatchException() {
        super(
                DEFAULT_MESSAGE, //message
                HttpStatus.UNAUTHORIZED, //status
                "PASSWORD_MISMATCH" //errorCode
        );
    }
    public PasswordMismatchException(String message) {
        super(
                message, //message
                HttpStatus.UNAUTHORIZED, //status
                "PASSWORD_MISMATCH" //errorCode
        );
    }
}
