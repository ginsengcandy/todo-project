package com.example.todo.todo.controller;

import com.example.todo.todo.dtos.loginDtos.SessionUser;
import com.example.todo.todo.dtos.todoDtos.*;
import com.example.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    //일정 생성
    @PostMapping("/todos")
    public ResponseEntity<CreateTodoResponse> create(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestBody CreateTodoRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.save(sessionUser, request));
    }
    //단건 조회
    @GetMapping("/todos/{todoId}")
    public ResponseEntity<GetTodoResponse> search(
            @PathVariable Long todoId) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.findOne(todoId));
    }

    //전체 조회 (특정 사용자 또는 전체 사용자)
    @GetMapping("/todos")
    public ResponseEntity<List<GetTodoResponse>> findAll(
            @RequestParam(required=false) Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.find(userId));
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<UpdateTodoResponse> update(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long todoId,
            @RequestBody UpdateTodoRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.update(sessionUser, todoId, request));
    }

    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long todoId){
        todoService.delete(sessionUser, todoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
