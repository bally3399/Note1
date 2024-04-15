package africa.semicolon.todo.utils;

import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.dtos.request.CreateTaskRequest;
import africa.semicolon.todo.dtos.request.LoginUserRequest;
import africa.semicolon.todo.dtos.request.RegisterUserRequest;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.dtos.response.UserResponse;

import java.time.LocalDateTime;

public class Mapper {
    public static Todo map(RegisterUserRequest registerUserRequest){
        Todo todo = new Todo();
        todo.setUsername(registerUserRequest.getUsername());
        todo.setPassword(registerUserRequest.getPassword());
        return todo;
    }
    public static UserResponse map(Todo savedTodo){
        UserResponse response = new UserResponse();
        if(savedTodo != null){
            response.setUsername(savedTodo.getUsername());
            response.setMessage("Successful");
        }
        return response;
    }
    public static Task map(CreateTaskRequest createTaskRequest){
        Task task = new Task();
        task.setTitle(createTaskRequest.getTitle());
        task.setDescription(createTaskRequest.getDescription());
        task.setTimeCreated(LocalDateTime.now());
        task.setAuthor(createTaskRequest.getAuthor());
        task.setId(task.getId());
        task.setStatus(createTaskRequest.getStatus());
        return task;
    }
    public static TaskResponse map(Task savedTask){
        TaskResponse response = new TaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setTimeCreated(savedTask.getTimeCreated());
        response.setTimeDone(savedTask.getTimeDone());
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        return response;
    }
    public static CreateTaskResponse mapTask(Task savedTask){
        CreateTaskResponse response = new CreateTaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setTimeCreated(savedTask.getTimeCreated());
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        return response;
    }
}
