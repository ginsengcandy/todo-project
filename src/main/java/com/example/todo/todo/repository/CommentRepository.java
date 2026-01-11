package com.example.todo.todo.repository;

import com.example.todo.todo.entity.Comment;
import com.example.todo.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTodo(Todo todo);
}
