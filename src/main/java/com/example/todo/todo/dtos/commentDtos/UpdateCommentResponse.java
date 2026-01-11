package com.example.todo.todo.dtos.commentDtos;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponse {
    private final Long id;
    private final String author;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateCommentResponse(Long id, String author, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
