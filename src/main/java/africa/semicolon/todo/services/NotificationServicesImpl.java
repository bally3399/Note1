package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Notification;
import africa.semicolon.todo.data.repositories.NotificationRepository;
import africa.semicolon.todo.dtos.request.DeleteNotificationRequest;
import africa.semicolon.todo.dtos.request.NotificationRequest;
import africa.semicolon.todo.dtos.request.ViewNotificationRequest;
import africa.semicolon.todo.dtos.response.NotificationResponse;
import africa.semicolon.todo.dtos.response.ViewNotificationResponse;
import africa.semicolon.todo.exceptions.NotificationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static africa.semicolon.todo.utils.Mapper.*;

@Service
@AllArgsConstructor
public class NotificationServicesImpl implements NotificationServices {
    private final NotificationRepository notificationRepository;
    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        Notification notification = map(notificationRequest);
        notificationRepository.save(notification);
        return mapNotification(notification);
    }

    @Override
    public Notification findNotificationById(String notificationId) {
        return notificationRepository.findNotificationById(notificationId);
    }

    @Override
    public String deleteNotification(DeleteNotificationRequest notificationRequest) {
        if(notificationRequest.getId().isEmpty()) throw new NotificationException("No notification found");
        Notification notification = notificationRepository.findNotificationById(notificationRequest.getId());
        notificationRepository.delete(notification);
        return "Notification deleted";
    }

    @Override
    public ViewNotificationResponse viewNotification(ViewNotificationRequest viewNotificationRequest) {
        Notification notification = mapView(viewNotificationRequest);
        notification.setSeen(true);
        notificationRepository.save(notification);
        return mapViewRes(notification);
    }
}
