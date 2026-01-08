package com.example.todo.todo.repository;

import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>{
    List<Todo> findByUser(User user);
}
