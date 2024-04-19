package africa.semicolon.todo.dtos.request;

import africa.semicolon.todo.data.model.Level;
import lombok.Data;

@Data
public class TaskInProgressRequest {
    private String id;
    private Level priority;
    private String author;

}
