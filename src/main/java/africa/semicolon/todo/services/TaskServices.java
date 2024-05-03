package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.*;

import java.util.List;

public interface TaskServices {
    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    List<Task> getAllTaskStarted(String user);

    List<Task> getAllTaskCreated(String user);

    List<Task> getAllTaskInProgress(String user);

    List<Task> getAllTaskCompleted(String user);

    List<Task> getTaskFor(String Username);

    void deleteAll();

    UpdateTaskResponse updateTask(UpdateTaskRequest task);

    List<Task> getAllTask();

    String deleteTask(DeleteTaskRequest deleteTaskRequest);

    TaskResponse taskPriorityTo(TaskPriorityToImportantRequest priority);

    TaskResponse taskPriorityTo(TaskPriorityToLessImportantRequest priority);

    TaskResponse taskPriorityToUrg(TaskPriorityToUrgentRequest priority);

    TaskResponse taskPriorityToLessUrg(TaskPriorityToLessUrgentRequest priority);

    TaskInProgressResponse taskInProgress(TaskInProgressRequest inProgressRequest);

    TaskDoneResponse taskCompleted(TaskCompletedRequest taskCompletedRequest);

    StartedTaskResponse startedTask(StartedTaskRequest startedTaskRequest);

    Task findTaskById(String id);

    AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest);
}
