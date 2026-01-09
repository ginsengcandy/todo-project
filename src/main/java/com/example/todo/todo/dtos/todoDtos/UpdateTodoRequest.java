package com.example.todo.todo.dtos.todoDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateTodoRequest {
    @NotBlank(message = "필수 입력 항목입니다.")
    @Size(max = 20, message = "글자 수 초과(20자)")
    private String title;
    @NotBlank(message = "필수 입력 항목입니다.")
    @Size(max = 200, message = "글자 수 초과(200자)")
    private String content;
}
