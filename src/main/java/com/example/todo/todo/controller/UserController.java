package com.example.todo.todo.controller;

import com.example.todo.todo.dtos.loginDtos.LoginRequest;
import com.example.todo.todo.dtos.loginDtos.LoginResponse;
import com.example.todo.todo.dtos.loginDtos.SessionUser;
import com.example.todo.todo.dtos.userDtos.*;
import com.example.todo.todo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session){
        LoginResponse loginResponse = userService.login(request);
        SessionUser sessionUser = new SessionUser(loginResponse.getId(), loginResponse.getEmail());
        session.setAttribute("loginUser", sessionUser);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session) {

        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> signup(
            @Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }
    //단건조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userId){
     return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
    //사용자가 스스로 수정
    @PutMapping("/users")
    public ResponseEntity<UpdateUserResponse> update(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody UpdateUserRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser, request));
    }
    //사용자가 스스로 삭제
    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ){
        userService.delete(sessionUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
