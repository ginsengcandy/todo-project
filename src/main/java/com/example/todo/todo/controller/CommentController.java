package com.example.todo.todo.controller;

import com.example.todo.todo.dtos.commentDtos.*;
import com.example.todo.todo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    //댓글 등록
    @PostMapping("/users/{userId}/todos/{todoId}/comments")
    public ResponseEntity<CreateCommentResponse> saveComment(
            @PathVariable Long todoId,
            @RequestBody CreateCommentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(todoId, request));
    }
    //댓글 단건 조회
    @GetMapping("/users/{userId}/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<GetCommentResponse> getOne(
            @PathVariable Long todoId,
            @PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findOne(todoId, commentId));
    };
    //댓글 전체 조회
    @GetMapping("/users/{userId}/todos/{todoId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getAll(
            @PathVariable Long todoId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(todoId));
    }
    //댓글 수정
    @PutMapping("/users/{userId}/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> update(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(todoId, commentId, request));
    }
    //댓글 삭제
    @DeleteMapping("/users/{userId}/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long todoId,
            @PathVariable Long commentId
    ){
        commentService.delete(todoId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
