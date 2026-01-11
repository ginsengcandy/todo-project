package com.example.todo.customErrors;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ServerException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 사용자입니다.";

    public UserNotFoundException(){
        super(
                DEFAULT_MESSAGE,
                HttpStatus.NOT_FOUND,
                "USER_NOT_FOUND"
        );
    }
    public UserNotFoundException(String message){
        super(
                message, //message
                HttpStatus.NOT_FOUND, //status
                "USER_NOT_FOUND" //errorCode
        );
    }
}
