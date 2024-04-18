package africa.semicolon.todo.utils;

import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.dtos.request.CreateTaskRequest;
import africa.semicolon.todo.dtos.request.RegisterUserRequest;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.dtos.response.UserResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {
    public static Todo map(RegisterUserRequest registerUserRequest){
        Todo todo = new Todo();
        todo.setUsername(registerUserRequest.getUsername().toLowerCase());
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
        task.setPriority(createTaskRequest.getDescription());
        task.setTimeCreated(LocalDateTime.now());
        task.setAuthor(createTaskRequest.getAuthor().toLowerCase());
        task.setId(task.getId());
        task.setStatus(Status.CREATED);
        return task;
    }
    public static TaskResponse map(Task savedTask){
        TaskResponse response = new TaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getPriority());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh-mm-ss").format(savedTask.getTimeCreated()));
        response.setTimeDone(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh-mm-ss").format(savedTask.getTimeDone()));
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        return response;
    }
    public static CreateTaskResponse mapTask(Task savedTask){
        CreateTaskResponse response = new CreateTaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getPriority());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh-mm-ss").format(savedTask.getTimeCreated()));
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        return response;
    }
}
