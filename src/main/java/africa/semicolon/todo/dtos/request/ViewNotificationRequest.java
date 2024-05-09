package africa.semicolon.todo.dtos.request;

import lombok.Data;

@Data
public class ViewNotificationRequest {
    private String username;
    private String notificationId;
}
