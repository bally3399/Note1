package africa.semicolon.todo.data.repositories;

import africa.semicolon.todo.data.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    Notification findNotificationById(String taskId);
}
