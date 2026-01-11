package com.example.todo.todo.service;

import com.example.todo.config.PasswordEncoder;
import com.example.todo.customErrors.PasswordMismatchException;
import com.example.todo.customErrors.UserNotFoundException;
import com.example.todo.todo.dtos.loginDtos.LoginRequest;
import com.example.todo.todo.dtos.loginDtos.LoginResponse;
import com.example.todo.todo.dtos.loginDtos.SessionUser;
import com.example.todo.todo.dtos.userDtos.*;
import com.example.todo.todo.entity.User;
import com.example.todo.todo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
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
               UserNotFoundException::new
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
                UserNotFoundException::new
        );
        user.update(request.getUsername(), request.getEmail(), request.getPassword());
        return new UpdateUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if(!existence) throw new UserNotFoundException();
        userRepository.deleteById(userId);
    }

    @Transactional
    public LoginResponse login(@Valid LoginRequest request, HttpSession session) {
        LoginResponse loginResponse = loginValidate(request);
        SessionUser sessionUser = new SessionUser(loginResponse.getId(), loginResponse.getEmail());
        session.setAttribute("loginUser", sessionUser);
        return loginResponse;
    }

    private LoginResponse loginValidate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 이메일입니다."));
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            throw new PasswordMismatchException();
        }

        return new LoginResponse(user.getId(), user.getEmail());
    }
}
