package africa.semicolon.todo.dtos.response;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import lombok.Data;

@Data
public class StartedTaskResponse {
    private String title;
    private String timeCreated;
    private Status status;
    private Level priority;
    private String author;
    private String description;
}
