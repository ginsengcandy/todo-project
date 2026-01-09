package com.example.todo.customErrors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class ServerException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String errorCode;
    public ServerException(String message, HttpStatus httpStatus, String errorCode){
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
