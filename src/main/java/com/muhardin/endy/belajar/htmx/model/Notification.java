package com.muhardin.endy.belajar.htmx.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(nullable = false, length = 50)
    private String type; // info, success, warning, error

    @Column(nullable = false)
    private boolean isRead = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public String getTypeColor() {
        return switch (type) {
            case "success" -> "green";
            case "warning" -> "yellow";
            case "error" -> "red";
            default -> "blue";
        };
    }

    public String getTypeIcon() {
        return switch (type) {
            case "success" -> "✅";
            case "warning" -> "⚠️";
            case "error" -> "❌";
            default -> "ℹ️";
        };
    }
}
