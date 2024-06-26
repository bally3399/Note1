package africa.semicolon.todo.dtos.response;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import lombok.Data;

@Data
public class TaskResponse {
    private String title;
    private String timeCreated;
    private String timeStarted;
    private String timeInProgress;
    private Status status;
    private Level priority;
    private String timeDone;
    private String author;
    private String description;
}
