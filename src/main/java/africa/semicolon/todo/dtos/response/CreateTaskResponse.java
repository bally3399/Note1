package africa.semicolon.todo.dtos.response;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateTaskResponse {
    private String title;
    private LocalDateTime timeCreated = LocalDateTime.now();
    private Status status;
    private Level description;
    private String author;
}
