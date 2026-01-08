package com.example.todo.todo.service;

import com.example.todo.todo.dtos.userDtos.CreateUserRequest;
import com.example.todo.todo.dtos.userDtos.CreateUserResponse;
import com.example.todo.todo.entity.User;
import com.example.todo.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public CreateUserResponse create(CreateUserRequest request) {
    User user = new User(
            request.getUsername(),
            request.getEmail()
    );
    User savedUser = userRepository.save(user);
    return new CreateUserResponse(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getCreatedAt(),
            savedUser.getModifiedAt()
    );
    }
}
