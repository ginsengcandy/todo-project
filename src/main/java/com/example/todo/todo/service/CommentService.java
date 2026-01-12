package com.example.todo.todo.service;


import com.example.todo.customErrors.CommentNotFoundException;
import com.example.todo.customErrors.ForbiddenException;
import com.example.todo.customErrors.TodoNotFoundException;
import com.example.todo.customErrors.UserNotFoundException;
import com.example.todo.todo.dtos.commentDtos.*;
import com.example.todo.todo.dtos.loginDtos.SessionUser;
import com.example.todo.todo.entity.Comment;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.entity.User;
import com.example.todo.todo.repository.CommentRepository;
import com.example.todo.todo.repository.TodoRepository;
import com.example.todo.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse save(SessionUser sessionUser, Long todoId, CreateCommentRequest request) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                UserNotFoundException::new
        );
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                TodoNotFoundException::new
        );
        Comment comment = new Comment(
                request.getAuthor(),
                request.getContent(),
                todo,
                user
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
    public GetCommentResponse findOne(Long commentId) {
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
    //전체조회(특정 일정의 모든 댓글)
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
    public UpdateCommentResponse update(SessionUser sessionUser, Long commentId, UpdateCommentRequest request) {
        //로그인 여부 확인
        if(sessionUser == null)
            throw new ForbiddenException();
        //세션유저가 DB상에 존재하는지 (방어적 코딩)
        Boolean userExistence = userRepository.existsById(sessionUser.getId());
        if(!userExistence) throw new UserNotFoundException();
        //해당 id의 댓글이 존재하는지
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                CommentNotFoundException::new
        );
        //접근하고자 하는 유저가 일정을 등록한 유저와 동일한지 (접근 권한 확인)
        if(!sessionUser.getId().equals(comment.getUser().getId())){
            //실패
            throw new ForbiddenException();
        }
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
    public void delete(SessionUser sessionUser, Long commentId) {
        //로그인되어있는지
        if(sessionUser == null)
            throw new ForbiddenException();
        //세션유저가 DB상에 존재하는지 (방어적 코딩)
        Boolean userExistence = userRepository.existsById(sessionUser.getId());
        if(!userExistence) throw new UserNotFoundException();
        //해당 id의 댓글이 존재하는지
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                CommentNotFoundException::new
        );
        //접근하고자 하는 유저가 일정을 등록한 유저와 동일한지 (접근 권한 확인)
        if(!sessionUser.getId().equals(comment.getUser().getId())){
            //실패
            throw new ForbiddenException();
        }
        commentRepository.deleteById(commentId);
    }

}
