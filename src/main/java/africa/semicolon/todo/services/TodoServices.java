package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.*;

import java.util.List;

public interface TodoServices {
    UserResponse registerUser(RegisterUserRequest registerUserRequest);

    LoginUserResponse login(LoginUserRequest loginUserRequest);

    String logout(LogoutRequest logoutRequest);

    Todo findByUser(String bally);

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    Task findTaskByTitle(String title);

    CreateTaskResponse updateTask(UpdateTaskRequest updateNoteRequest);

    String deleteTask(DeleteTaskRequest deleteTaskRequest);

    List<Task> getAllTask();

    List<Task> getAllStartedTask(String user);

    List<Task> getTaskFor(String user);

    List<Task> getAllTaskCompleted(String user);

    List<Task> getAllTaskCreated(String user);

    List<Task> getAllTaskInProgress(String user);

    TaskResponse taskPriorityTo(TaskPriorityToImportantRequest priority);

    TaskResponse taskPriorityTo(TaskPriorityToLessImportantRequest priority);

    TaskResponse taskPriorityTo(TaskPriorityToUrgentRequest priority);

    TaskResponse taskPriorityTo(TaskPriorityToLessUrgentRequest priority);

    TaskResponse taskInProgress(TaskInProgressRequest inProgressRequest);

    CreateTaskResponse startedTask(StartedTaskRequest startedTaskRequest);

    TaskResponse taskCompleted(TaskCompletedRequest taskCompletedRequest);
}
