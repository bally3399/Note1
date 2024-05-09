package africa.semicolon.todo.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("notifications")
public class Notification {
    private String message;
    private String receiver;
    private LocalDateTime timestamp;
    private String taskId;
    @Id
    private String id;
    private boolean isSeen;
}
