package com.example.todo.customErrors;

import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends ServerException {
    public PasswordMismatchException(String message) {
        super(
                message, //message
                HttpStatus.UNAUTHORIZED, //status
                "PASSWORD_MISMATCH" //errorCode
        );
    }
}
