package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.dtos.response.UserResponse;

import java.util.List;

public interface TodoServices {
    UserResponse registerUser(RegisterUserRequest registerUserRequest);

    UserResponse login(LoginUserRequest loginUserRequest);

    String logout(LogoutRequest logoutRequest);

    Todo findByUser(String bally);

    TaskResponse createTask(CreateTaskRequest createTaskRequest);

    Task findTaskByTitle(String title);

    TaskResponse updateTask(UpdateTaskRequest updateNoteRequest);

    String deleteTask(CreateTaskRequest createTaskRequest);

    List<Task> getAllTask();

    TaskResponse taskInProgress(TaskInProgressRequest inProgressRequest);

    TaskResponse taskCompleted(TaskCompletedRequest taskCompletedRequest);
}
