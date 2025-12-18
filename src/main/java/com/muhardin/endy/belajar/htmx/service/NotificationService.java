package com.muhardin.endy.belajar.htmx.service;

import com.muhardin.endy.belajar.htmx.model.Notification;
import com.muhardin.endy.belajar.htmx.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final Random random = new Random();

    private static final String[] TYPES = {"info", "success", "warning", "error"};

    private static final String[][] NOTIFICATIONS = {
            {"System Update", "A new system update is available for installation."},
            {"Backup Complete", "Your daily backup has been completed successfully."},
            {"New Message", "You have received a new message from the administrator."},
            {"Disk Space Warning", "Disk space is running low. Please free up some space."},
            {"Security Alert", "Unusual activity detected on your account."},
            {"Task Completed", "Your scheduled task has finished executing."},
            {"New User Registered", "A new user has registered on the platform."},
            {"Payment Received", "Payment for invoice #12345 has been received."},
            {"Server Status", "All systems are operational and running normally."},
            {"Maintenance Scheduled", "System maintenance is scheduled for this weekend."}
    };

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(fixedRate = 15000) // Every 15 seconds
    public void generateRandomNotification() {
        // 30% chance to generate a notification
        if (random.nextInt(100) < 30) {
            int index = random.nextInt(NOTIFICATIONS.length);
            String[] notif = NOTIFICATIONS[index];
            String type = TYPES[random.nextInt(TYPES.length)];

            Notification notification = new Notification();
            notification.setTitle(notif[0]);
            notification.setMessage(notif[1]);
            notification.setType(type);
            notification.setRead(false);

            notificationRepository.save(notification);
        }
    }

    public long getUnreadCount() {
        return notificationRepository.countByIsReadFalse();
    }
}
