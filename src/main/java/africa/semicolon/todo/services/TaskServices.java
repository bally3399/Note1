package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.dtos.request.CreateTaskRequest;
import africa.semicolon.todo.dtos.request.UpdateTaskRequest;
import africa.semicolon.todo.dtos.response.TaskResponse;

import java.util.List;

public interface TaskServices {
    TaskResponse createTask(CreateTaskRequest createTaskRequest);

    List<Task> getTaskFor(String Username);

    void deleteAll();

    TaskResponse updateTask(UpdateTaskRequest task);

    List<Task> getAllTask();

    String deleteTask(CreateTaskRequest createTaskRequest);
}