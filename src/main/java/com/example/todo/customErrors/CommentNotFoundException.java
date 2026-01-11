package com.example.todo.customErrors;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ServerException {
    private static final String DEFAULT_MESSAGE = "댓글이 존재하지 않습니다.";
    public CommentNotFoundException(){
        super(
                DEFAULT_MESSAGE, //message
                HttpStatus.NOT_FOUND, //status
                "COMMENT_NOT_FOUND" //errorCode
        );
    }public CommentNotFoundException(String message){
        super(
                message, //message
                HttpStatus.NOT_FOUND, //status
                "COMMENT_NOT_FOUND" //errorCode
        );
    }
}
