package africa.semicolon.todo.data.repositories;

import africa.semicolon.todo.data.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    Notification findNotificationById(String notificationId);
}
