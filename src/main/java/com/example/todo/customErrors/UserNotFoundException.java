package com.example.todo.customErrors;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ServerException {
    public UserNotFoundException(String message){
        super(
                message, //message
                HttpStatus.NOT_FOUND, //status
                "USER_NOT_FOUND" //errorCode
        );
    }
}
