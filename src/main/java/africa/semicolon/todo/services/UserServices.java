package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.User;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.*;

import java.util.List;

public interface UserServices {
    UserResponse registerUser(RegisterUserRequest registerUserRequest);

    LoginUserResponse login(LoginUserRequest loginUserRequest);

    String logout(LogoutRequest logoutRequest);

    User findByUser(String bally);

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    Task findTaskByTitle(String title);

    UpdateTaskResponse updateTask(UpdateTaskRequest updateNoteRequest);

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

    TaskInProgressResponse taskInProgress(TaskInProgressRequest inProgressRequest);

    StartedTaskResponse startedTask(StartedTaskRequest startedTaskRequest);

    TaskDoneResponse taskCompleted(TaskCompletedRequest taskCompletedRequest);

    AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest);

}
