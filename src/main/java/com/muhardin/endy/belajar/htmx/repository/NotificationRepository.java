package com.muhardin.endy.belajar.htmx.repository;

import com.muhardin.endy.belajar.htmx.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByOrderByCreatedAtDesc();
    long countByIsReadFalse();
    List<Notification> findTop5ByOrderByCreatedAtDesc();
}
