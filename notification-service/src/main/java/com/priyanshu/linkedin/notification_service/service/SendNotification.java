package com.priyanshu.linkedin.notification_service.service;

import com.priyanshu.linkedin.notification_service.entity.Notification;
import com.priyanshu.linkedin.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendNotification {

    private final NotificationRepository notificationRepository;

    public void send(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);

        notificationRepository.save(notification);
        log.info("Notification saved for userId: {}", userId);
    }
}
