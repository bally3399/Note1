package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Notification;
import africa.semicolon.todo.data.repositories.NotificationRepository;
import africa.semicolon.todo.dtos.request.NotificationRequest;
import africa.semicolon.todo.dtos.response.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static africa.semicolon.todo.utils.Mapper.map;
import static africa.semicolon.todo.utils.Mapper.mapNotification;
@Service
public class NotificationServicesImpl implements NotificationServices {
    @Autowired
    private NotificationRepository repository;
    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        Notification notification = map(notificationRequest);
        return mapNotification(notification);
    }

    @Override
    public Notification findNotificationById(String notificationId) {
        return repository.findNotificationById(notificationId);
    }
}
