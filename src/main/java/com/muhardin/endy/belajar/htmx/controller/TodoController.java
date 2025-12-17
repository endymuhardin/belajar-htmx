package com.muhardin.endy.belajar.htmx.controller;

import com.muhardin.endy.belajar.htmx.model.Todo;
import com.muhardin.endy.belajar.htmx.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("todos", todoService.findAll());
        model.addAttribute("totalCount", todoService.count());
        model.addAttribute("completedCount", todoService.countCompleted());
        return "fragments/todo-list";
    }

    @PostMapping
    public String create(@RequestParam String title, Model model) {
        if (title == null || title.trim().isEmpty()) {
            model.addAttribute("todos", todoService.findAll());
            model.addAttribute("totalCount", todoService.count());
            model.addAttribute("completedCount", todoService.countCompleted());
            model.addAttribute("error", "Title tidak boleh kosong");
            return "fragments/todo-list";
        }

        todoService.create(title.trim());
        model.addAttribute("todos", todoService.findAll());
        model.addAttribute("totalCount", todoService.count());
        model.addAttribute("completedCount", todoService.countCompleted());
        return "fragments/todo-list";
    }

    @PutMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, Model model) {
        Todo todo = todoService.toggleComplete(id);
        if (todo != null) {
            model.addAttribute("todo", todo);
            return "fragments/todo-item";
        }
        // Jika todo tidak ditemukan, kembalikan response kosong
        // Return null akan membuat Spring tidak render template apapun
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
