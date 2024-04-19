package africa.semicolon.todo.dtos.request;

import africa.semicolon.todo.data.model.Level;
import lombok.Data;

@Data
public class CreateTaskRequest {
    private String title;
    private Level priority;
    private String author;
    private String description;
}
