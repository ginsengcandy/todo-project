package com.example.todo.todo.controller;

import com.example.todo.todo.dtos.userDtos.CreateUserRequest;
import com.example.todo.todo.dtos.userDtos.CreateUserResponse;
import com.example.todo.todo.dtos.userDtos.GetUserResponse;
import com.example.todo.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> create(
            @RequestBody CreateUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }
    //단건조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userId){
     return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
}
