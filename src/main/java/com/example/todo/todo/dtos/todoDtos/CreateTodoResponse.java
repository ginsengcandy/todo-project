package com.example.todo.todo.dtos.todoDtos;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CreateTodoResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public CreateTodoResponse(Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
