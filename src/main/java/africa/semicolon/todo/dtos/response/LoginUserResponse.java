package africa.semicolon.todo.dtos.response;

import africa.semicolon.todo.data.model.Notification;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginUserResponse {
    private String username;
    private String message;
    private List<Notification> notificationList = new ArrayList<>();
}
