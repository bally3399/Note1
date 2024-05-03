package africa.semicolon.todo.dtos.request;

import africa.semicolon.todo.data.model.Status;
import lombok.Data;

@Data
public class TaskPriorityToLessImportantRequest {
    private String id;
    private String author;

}
