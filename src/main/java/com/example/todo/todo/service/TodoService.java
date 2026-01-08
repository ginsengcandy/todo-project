package com.example.todo.todo.service;

import com.example.todo.todo.dtos.todoDtos.*;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional
    public CreateTodoResponse save(CreateTodoRequest request){
        Todo todo = new Todo(
                request.getUsername(),
                request.getTitle(),
                request.getContent());
        Todo savedTodo = todoRepository.save(todo);
        return new CreateTodoResponse(
                savedTodo.getId(),
                savedTodo.getUsername(),
                savedTodo.getTitle(),
                savedTodo.getContent(),
                savedTodo.getCreatedAt(),
                savedTodo.getModifiedAt()
                );
    }

    //단건조회
    @Transactional(readOnly=true)
    public GetTodoResponse findOne(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("일정이 존재하지 않습니다.")
        );
        return new GetTodoResponse(
                todo.getId(),
                todo.getUsername(),
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
    @Transactional(readOnly=true)
    public List<GetTodoResponse> findAll() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(todo -> new GetTodoResponse(
                        todo.getId(),
                        todo.getUsername(),
                        todo.getTitle(),
                        todo.getContent(),
                        todo.getCreatedAt(),
                        todo.getModifiedAt()
                )).toList();
    }
    @Transactional
    public UpdateTodoResponse update(Long todoId, UpdateTodoRequest request) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("일정이 존재하지 않습니다.")
        );
        todo.update(
                request.getTitle(),
                request.getContent()
        );
        return new UpdateTodoResponse(
                todo.getId(),
                todo.getUsername(),
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    public void delete(Long todoId) {
        Boolean existence = todoRepository.existsById(todoId);
        if(!existence)
            throw new IllegalStateException("게시글이 존재하지 않습니다.");
        todoRepository.deleteById(todoId);
    }
}
