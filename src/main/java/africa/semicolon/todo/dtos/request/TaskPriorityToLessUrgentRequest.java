package africa.semicolon.todo.dtos.request;

import africa.semicolon.todo.data.model.Status;
import lombok.Data;

@Data
public class TaskPriorityToLessUrgentRequest {
    private String id;
    private Status status;
    private String author;
}
