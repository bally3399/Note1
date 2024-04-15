package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;

import java.util.List;

public interface TaskServices {
    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    List<Task> getAllTaskStarted();

    List<Task> getAllTaskCreated();

    List<Task> getAllTaskInProgress();

    List<Task> getAllTaskCompleted();

    List<Task> getTaskFor(String Username);

    void deleteAll();

    CreateTaskResponse updateTask(UpdateTaskRequest task);

    List<Task> getAllTask();

    String deleteTask(CreateTaskRequest createTaskRequest);

    TaskResponse taskInProgress(TaskInProgressRequest inProgressRequest);

    TaskResponse taskCompleted(TaskCompletedRequest taskCompletedRequest);

    CreateTaskResponse startedTask(StartedTaskRequest startedTaskRequest);
}
