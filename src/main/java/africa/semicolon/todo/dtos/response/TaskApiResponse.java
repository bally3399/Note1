package africa.semicolon.todo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskApiResponse {
    boolean isSuccessful;
    Object TaskResponse;
}
