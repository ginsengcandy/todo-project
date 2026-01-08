package com.example.todo.todo.dtos.userDtos;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String username;
    private String email;
}
