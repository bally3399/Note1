package africa.semicolon.todo.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewNotificationResponse {
    private String message;
//    private String taskId;
    private String notificationId;
    private LocalDateTime timeStamp = LocalDateTime.now();
    private String username;
}
