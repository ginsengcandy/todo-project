package com.example.todo.todo.repository;

import com.example.todo.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
