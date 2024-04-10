package africa.semicolon.todo.dtos.request;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String title;
    private String newTitle;
    private Level description;
    private Level newDescription;
    private Status status;
    private Status newStatus;
    private String author;
}
