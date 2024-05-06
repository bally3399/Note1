package africa.semicolon.todo.dtos.response;

import lombok.Data;

@Data
public class NotificationResponse {
    private String message;
    private String taskId;
    private String notificationId;
    private String timeStamp;
    private String username;
}
