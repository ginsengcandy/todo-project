package com.example.todo.todo.dtos.userDtos;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
}
