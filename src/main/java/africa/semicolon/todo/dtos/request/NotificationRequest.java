package africa.semicolon.todo.dtos.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private String username;
    private String message;
    private String taskId;
}
