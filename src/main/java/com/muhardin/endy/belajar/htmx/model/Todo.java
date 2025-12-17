package com.muhardin.endy.belajar.htmx.model;

import java.time.LocalDateTime;

public class Todo {
    private Long id;
    private String title;
    private boolean completed;
    private LocalDateTime createdAt;

    public Todo() {
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }

    public Todo(Long id, String title) {
        this();
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
