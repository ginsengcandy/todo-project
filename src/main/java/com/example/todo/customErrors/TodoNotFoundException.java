package com.example.todo.customErrors;

import org.springframework.http.HttpStatus;

public class TodoNotFoundException extends ServerException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 일정입니다.";

    public TodoNotFoundException(){
        super(
                DEFAULT_MESSAGE, //message
                HttpStatus.NOT_FOUND, //status
                "TODO_NOT_FOUND" //errorCode
        );
    }
    public TodoNotFoundException(String message){
        super(
                message, //message
                HttpStatus.NOT_FOUND, //status
                "TODO_NOT_FOUND" //errorCode
        );
    }
}
