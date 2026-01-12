package com.example.todo.todo.controller;

import com.example.todo.todo.dtos.commentDtos.*;
import com.example.todo.todo.dtos.loginDtos.SessionUser;
import com.example.todo.todo.service.CommentService;
import jakarta.validation.Valid;
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
    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CreateCommentResponse> saveComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long todoId,
            @RequestBody CreateCommentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(sessionUser, todoId, request));
    }
    //댓글 단건 조회
    @GetMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<GetCommentResponse> getOne(
            @PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findOne(commentId));
    };
    //댓글 전체 조회
    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getAll(
            @PathVariable Long todoId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(todoId));
    }
    //댓글 수정
    @PutMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> update(
            @Valid @SessionAttribute(name = "loginUser") SessionUser sessionUser,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(sessionUser, commentId, request));
    }
    //댓글 삭제
    @DeleteMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @Valid @SessionAttribute(name = "loginUser") SessionUser sessionUser,
            @PathVariable Long commentId){
        commentService.delete(sessionUser, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
