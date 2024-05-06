package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Notification;
import africa.semicolon.todo.dtos.request.NotificationRequest;
import africa.semicolon.todo.dtos.response.NotificationResponse;
import org.springframework.stereotype.Service;

@Service
public interface NotificationServices {
    NotificationResponse sendNotification(NotificationRequest notificationRequest);

    Notification findNotificationById(String notificationId);
}
