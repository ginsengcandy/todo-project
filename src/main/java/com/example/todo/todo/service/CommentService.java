package com.example.todo.todo.service;


import com.example.todo.customErrors.CommentNotFoundException;
import com.example.todo.customErrors.TodoNotFoundException;
import com.example.todo.todo.dtos.commentDtos.*;
import com.example.todo.todo.entity.Comment;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.repository.CommentRepository;
import com.example.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public CreateCommentResponse save(Long todoId, CreateCommentRequest request) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                TodoNotFoundException::new
        );
        Comment comment = new Comment(
                request.getAuthor(),
                request.getContent(),
                todo
        );
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getAuthor(),
                savedComment.getContent(),
                savedComment.getCreatedAt()
        );
    }
    //단건조회
    @Transactional(readOnly=true)
    public GetCommentResponse findOne(Long todoId, Long commentId) {
        if(!todoRepository.existsById(todoId))
            throw new TodoNotFoundException();
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                CommentNotFoundException::new
        );
        return new GetCommentResponse(
                comment.getId(),
                comment.getAuthor(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
    //전체조회
    @Transactional(readOnly=true)
    public List<GetCommentResponse> findAll(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                TodoNotFoundException::new
        );
        List<Comment> comments = commentRepository.findByTodo(todo);
        return comments.stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                        comment.getAuthor(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).toList();
    }

    @Transactional
    public UpdateCommentResponse update(Long todoId, Long commentId, UpdateCommentRequest request) {
        if(!todoRepository.existsById(todoId))
            throw new TodoNotFoundException();
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                CommentNotFoundException::new
        );
        comment.update(request.getContent());
        return new UpdateCommentResponse(
                comment.getId(),
                comment.getAuthor(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long todoId, Long commentId) {
        if(!todoRepository.existsById(todoId))
            throw new TodoNotFoundException();
        Boolean existence = commentRepository.existsById(commentId);
        if(!existence)
            throw new CommentNotFoundException();
        commentRepository.deleteById(commentId);
    }

}
