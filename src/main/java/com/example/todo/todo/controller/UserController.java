package com.example.todo.todo.controller;

import com.example.todo.todo.dtos.userDtos.CreateUserRequest;
import com.example.todo.todo.dtos.userDtos.CreateUserResponse;
import com.example.todo.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> create(
            @RequestBody CreateUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }
}
