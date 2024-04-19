package africa.semicolon.todo.dtos.request;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import lombok.Data;

@Data
public class TaskCompletedRequest {
    private String id;
    private Level priority;
    private String author;
}
