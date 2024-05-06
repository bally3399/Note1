package africa.semicolon.todo.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Todos")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean isLoggedIn = false;
    private LocalDateTime timeCreated = LocalDateTime.now();
    @DBRef
    private List<Task> tasks = new ArrayList<>();
    @DBRef
    private List<Notification> notifications = new ArrayList<>();
}
