package com.example.todo.todo.dtos.todoDtos;

import lombok.Getter;

@Getter
public class UpdateTodoRequest {
    //제목, 내용만 바꿀 수 있음
    private String title;
    private String content;
}
