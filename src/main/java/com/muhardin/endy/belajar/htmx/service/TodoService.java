package com.muhardin.endy.belajar.htmx.service;

import com.muhardin.endy.belajar.htmx.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final Map<Long, Todo> todos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Todo> findAll() {
        return new ArrayList<>(todos.values());
    }

    public Todo findById(Long id) {
        return todos.get(id);
    }

    public Todo create(String title) {
        Long id = idGenerator.getAndIncrement();
        Todo todo = new Todo(id, title);
        todos.put(id, todo);
        return todo;
    }

    public Todo toggleComplete(Long id) {
        Todo todo = todos.get(id);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
        }
        return todo;
    }

    public void delete(Long id) {
        todos.remove(id);
    }

    public long count() {
        return todos.size();
    }

    public long countCompleted() {
        return todos.values().stream()
                .filter(Todo::isCompleted)
                .count();
    }
}
