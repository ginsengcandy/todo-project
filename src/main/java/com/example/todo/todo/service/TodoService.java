package com.example.todo.todo.service;

import com.example.todo.customErrors.ForbiddenException;
import com.example.todo.customErrors.TodoNotFoundException;
import com.example.todo.customErrors.UserNotFoundException;
import com.example.todo.todo.dtos.loginDtos.SessionUser;
import com.example.todo.todo.dtos.todoDtos.*;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.entity.User;
import com.example.todo.todo.repository.TodoRepository;
import com.example.todo.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateTodoResponse save(SessionUser sessionUser, CreateTodoRequest request){
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                UserNotFoundException::new
        );
        Todo todo = new Todo(
                request.getTitle(),
                request.getContent(),
                user
        );
        Todo savedTodo = todoRepository.save(todo);
        return new CreateTodoResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContent(),
                savedTodo.getCreatedAt()
                );
    }
    //단건조회
    @Transactional(readOnly=true)
    public GetTodoResponse findOne(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                TodoNotFoundException::new
        );
        return new GetTodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    @Transactional(readOnly=true)
    public List<GetTodoResponse> find(Long userId) {
        if(userId != null) {
            return findByUser(userId);
        }
        return findAll();
    }
    //특정 유저의 모든 일정
    private List<GetTodoResponse> findByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                UserNotFoundException::new
        );
        List<Todo> todos = todoRepository.findByUser(user);
        return todos.stream()
                .map(todo -> new GetTodoResponse(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getContent(),
                        todo.getCreatedAt(),
                        todo.getModifiedAt()
                )).toList();
    }
    //모든 유저의 모든 일정
    private List<GetTodoResponse> findAll(){
        List<Todo> todos = todoRepository.findAll();
        //방법 1 - List로 가져오기
        List<GetTodoResponse> dtos = new ArrayList<>();
        for (Todo todo : todos) {
            dtos.add(new GetTodoResponse(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getContent(),
                    todo.getCreatedAt(),
                    todo.getModifiedAt()
            ));
        }
        return dtos;
    }

    @Transactional
    public UpdateTodoResponse update(SessionUser sessionUser, Long todoId, UpdateTodoRequest request) {
        //로그인되어 있는지
        if(sessionUser==null) throw new ForbiddenException();
        //세션유저가 DB상에 존재하는지 (방어적 코딩)
        Boolean userExistence = userRepository.existsById(sessionUser.getId());
        if(!userExistence) throw new UserNotFoundException();
        //해당 id의 일정이 존재하는지
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                TodoNotFoundException::new
        );
        //접근하고자 하는 유저가 일정을 등록한 유저와 동일한지 (접근 권한 확인)
        if(!sessionUser.getId().equals(todo.getUser().getId())){
            //실패
            throw new ForbiddenException();
        }
        todo.update(
                request.getTitle(),
                request.getContent()
        );
        return new UpdateTodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
    @Transactional
    public void delete(SessionUser sessionUser, Long todoId) {
        //로그인되어있는지
        if(sessionUser==null) throw new ForbiddenException();
        //세션유저가 DB상에 존재하는지 (방어적 코딩)
        Boolean userExistence = userRepository.existsById(sessionUser.getId());
        if(!userExistence) throw new UserNotFoundException();
        //해당 id의 일정이 존재하는지
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                TodoNotFoundException::new
        );
        //접근하고자 하는 유저가 일정을 등록한 유저와 동일한지 (접근 권한 확인)
        if(!sessionUser.getId().equals(todo.getUser().getId())){
            //실패
            throw new ForbiddenException();
        }
        todoRepository.deleteById(todoId);
    }
}
