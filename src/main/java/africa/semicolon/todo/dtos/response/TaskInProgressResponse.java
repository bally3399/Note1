package africa.semicolon.todo.dtos.response;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskInProgressResponse {
    private String title;
    private String timeStarted;
    private String timeInProgress;
    private Status status;
    private Level priority;
    private String author;
    private String description;
}
