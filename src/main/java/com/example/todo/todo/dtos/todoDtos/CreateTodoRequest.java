package com.example.todo.todo.dtos.todoDtos;

import lombok.Getter;

@Getter
public class CreateTodoRequest {
    private String username;
    private String title;
    private String content;
}
