package com.example.todo.todo.service;

import com.example.todo.customErrors.TodoNotFoundException;
import com.example.todo.customErrors.UserNotFoundException;
import com.example.todo.todo.dtos.todoDtos.*;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.entity.User;
import com.example.todo.todo.repository.TodoRepository;
import com.example.todo.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateTodoResponse save(Long userId, CreateTodoRequest request){
        User user = userRepository.findById(userId).orElseThrow(
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
    public List<GetTodoResponse> findAll(Long userId) {
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
    @Transactional
    public UpdateTodoResponse update(Long todoId, UpdateTodoRequest request) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                TodoNotFoundException::new
        );
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
    public void delete(Long todoId) {
        Boolean existence = todoRepository.existsById(todoId);
        if(!existence)
            throw new TodoNotFoundException();
        todoRepository.deleteById(todoId);
    }
}
