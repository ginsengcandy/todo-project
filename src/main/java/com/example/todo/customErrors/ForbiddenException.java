package com.example.todo.customErrors;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ServerException {
    private static final String DEFAULT_MESSAGE = "접근 권한이 없습니다.";

    public ForbiddenException(){
        super(
                DEFAULT_MESSAGE, //message
                HttpStatus.FORBIDDEN, //status
                "ACCESS_DENIED" //errorCode
        );
    }
    public ForbiddenException(String message){
        super(
                message, //message
                HttpStatus.NOT_FOUND, //status
                "ACCESS_DENIED" //errorCode
        );
    }
}
