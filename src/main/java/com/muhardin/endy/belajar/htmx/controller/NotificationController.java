package com.muhardin.endy.belajar.htmx.controller;

import com.muhardin.endy.belajar.htmx.model.Notification;
import com.muhardin.endy.belajar.htmx.repository.NotificationRepository;
import com.muhardin.endy.belajar.htmx.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    public NotificationController(NotificationRepository notificationRepository, NotificationService notificationService) {
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/polling")
    public String pollingDemo(Model model) {
        model.addAttribute("notifications", notificationRepository.findTop5ByOrderByCreatedAtDesc());
        model.addAttribute("unreadCount", notificationService.getUnreadCount());
        return "polling-demo";
    }

    @GetMapping("/notifications/count")
    public String getNotificationCount(Model model) {
        long unreadCount = notificationService.getUnreadCount();
        model.addAttribute("count", unreadCount);
        return "fragments/notification-count";
    }

    @GetMapping("/notifications/list")
    public String getNotificationList(Model model) {
        List<Notification> notifications = notificationRepository.findTop5ByOrderByCreatedAtDesc();
        model.addAttribute("notifications", notifications);
        return "fragments/notification-list";
    }

    @GetMapping("/notifications/all")
    public String getAllNotifications(Model model) {
        model.addAttribute("notifications", notificationRepository.findAllByOrderByCreatedAtDesc());
        return "fragments/notification-list";
    }

    @PostMapping("/notifications/{id}/mark-read")
    public String markAsRead(@PathVariable Long id, Model model) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);

        model.addAttribute("notifications", notificationRepository.findTop5ByOrderByCreatedAtDesc());
        return "fragments/notification-list";
    }

    @DeleteMapping("/notifications/{id}")
    public String deleteNotification(@PathVariable Long id, Model model) {
        notificationRepository.deleteById(id);
        model.addAttribute("notifications", notificationRepository.findTop5ByOrderByCreatedAtDesc());
        return "fragments/notification-list";
    }

    @PostMapping("/notifications/clear-all")
    public String clearAllNotifications(Model model) {
        notificationRepository.deleteAll();
        model.addAttribute("notifications", List.of());
        return "fragments/notification-list";
    }
}
