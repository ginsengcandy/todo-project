package com.example.todo.todo.dtos.commentDtos;

import com.example.todo.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {
    private final Long id;
    private final String author;
    private final String content;
    private final LocalDateTime createdAt;

    public CreateCommentResponse(Long id, String author, String content, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }
}
