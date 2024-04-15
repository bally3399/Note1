package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.dtos.response.UserResponse;

import java.util.List;

public interface TodoServices {
    UserResponse registerUser(RegisterUserRequest registerUserRequest);

    UserResponse login(LoginUserRequest loginUserRequest);

    String logout(LogoutRequest logoutRequest);

    Todo findByUser(String bally);

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    Task findTaskByTitle(String title);

    CreateTaskResponse updateTask(UpdateTaskRequest updateNoteRequest);

    String deleteTask(CreateTaskRequest createTaskRequest);

    List<Task> getAllTask();

    List<Task> getAllStartedTask();

    List<Task> getAllTaskCompleted();

    List<Task> getAllTaskCreated();

    List<Task> getAllTaskInProgress();

    TaskResponse taskInProgress(TaskInProgressRequest inProgressRequest);

    CreateTaskResponse startedTask(StartedTaskRequest startedTaskRequest);

    TaskResponse taskCompleted(TaskCompletedRequest taskCompletedRequest);
}
