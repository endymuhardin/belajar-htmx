package com.muhardin.endy.belajar.htmx.service;

import com.muhardin.endy.belajar.htmx.model.Todo;
import com.muhardin.endy.belajar.htmx.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAllByOrderByCreatedAtDesc();
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    public Todo create(String title) {
        Todo todo = new Todo(null, title);
        return todoRepository.save(todo);
    }

    public Todo toggleComplete(Long id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
        }
        return todo;
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    public long count() {
        return todoRepository.count();
    }

    public long countCompleted() {
        return todoRepository.findAll().stream()
                .filter(Todo::isCompleted)
                .count();
    }
}

