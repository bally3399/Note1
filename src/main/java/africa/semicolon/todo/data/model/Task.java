package africa.semicolon.todo.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private Status status;
    private Level priority;
    private LocalDateTime timeCreated = LocalDateTime.now();
    private LocalDateTime timeDone = LocalDateTime.now();
    private String author;
}
