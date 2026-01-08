package com.example.todo.todo.service;

import com.example.todo.todo.dtos.userDtos.*;
import com.example.todo.todo.entity.User;
import com.example.todo.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly=true)
    public List<GetUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new GetUserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                )
                ).toList();
    }

    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("사용자가 존재하지 않습니다.")
        );
        user.update(request.getUsername(), request.getEmail());
        return new UpdateUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
