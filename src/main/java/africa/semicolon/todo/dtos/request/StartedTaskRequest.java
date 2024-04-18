package africa.semicolon.todo.dtos.request;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import lombok.Data;

@Data
public class StartedTaskRequest {
    private String title;
    private Level description;
    private String author;
}
