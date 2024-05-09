package africa.semicolon.todo.dtos.request;

import lombok.Data;

@Data
public class DeleteNotificationRequest {
    private String id;
    private String username;
}
