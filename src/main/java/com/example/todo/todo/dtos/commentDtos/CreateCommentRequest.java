package com.example.todo.todo.dtos.commentDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @NotBlank(message = "필수 입력 항목입니다.")
    @Size(max = 20, message = "글자 수 초과(20자)")
        private String author;
    @NotBlank(message = "필수 입력 항목입니다.")
    @Size(max = 100, message = "글자 수 초과(100자)")
        private String content;
}
