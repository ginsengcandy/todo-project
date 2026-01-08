package com.example.todo.todo.service;

import com.example.todo.todo.dtos.userDtos.CreateUserRequest;
import com.example.todo.todo.dtos.userDtos.CreateUserResponse;
import com.example.todo.todo.dtos.userDtos.GetUserResponse;
import com.example.todo.todo.entity.User;
import com.example.todo.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
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

    @Transactional(readOnly=true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("사용자가 존재하지 않습니다.")
        );
        return new GetUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
