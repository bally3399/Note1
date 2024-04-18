package africa.semicolon.todo.dtos.response;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskResponse {
    private String title;
    private String timeCreated;
    private Status status;
    private Level description;
    private String timeDone;
    private String author;
}
