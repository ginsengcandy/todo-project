package com.example.todo.todo.controller;

import com.example.todo.todo.dtos.todoDtos.*;
import com.example.todo.todo.service.TodoService;
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
            @RequestBody CreateTodoRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.save(request));
    }
    //단건 조회
    @GetMapping("/todos/{todoId}")
    public ResponseEntity<GetTodoResponse> search(
            @PathVariable Long todoId) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.findOne(todoId));
    }

    //전체 조회
    @GetMapping("/todos")
    public ResponseEntity<List<GetTodoResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.findAll());
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<UpdateTodoResponse> update(
            @PathVariable Long todoId,
            @RequestBody UpdateTodoRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.update(todoId, request));
    }

    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<Void> delete(@PathVariable Long todoId){
        todoService.delete(todoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
