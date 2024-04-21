package africa.semicolon.todo.utils;

import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.dtos.request.CreateTaskRequest;
import africa.semicolon.todo.dtos.request.LoginUserRequest;
import africa.semicolon.todo.dtos.request.RegisterUserRequest;
import africa.semicolon.todo.dtos.response.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {
    public static Todo map(RegisterUserRequest registerUserRequest) {
        Todo todo = new Todo();
        todo.setUsername(registerUserRequest.getUsername().toLowerCase());
        todo.setPassword(registerUserRequest.getPassword());
        return todo;
    }

    public static UserResponse map(Todo savedTodo, RegisterUserRequest registerUserRequest) {
        UserResponse response = new UserResponse();
        response.setUsername(registerUserRequest.getUsername());
        response.setMessage("Successful");
        return response;
    }

    public static LoginUserResponse mapLogin(Todo savedTodo, LoginUserRequest loginUserRequest) {
        LoginUserResponse response = new LoginUserResponse();
        response.setUsername(loginUserRequest.getUsername());
        response.setMessage("Successful");
        return response;
    }

    public static Task map(CreateTaskRequest createTaskRequest) {
        Task task = new Task();
        task.setTitle(createTaskRequest.getTitle());
        task.setPriority(createTaskRequest.getPriority());
        task.setDescription(createTaskRequest.getDescription());
        task.setTimeCreated(LocalDateTime.now());
        task.setAuthor(createTaskRequest.getAuthor().toLowerCase());
        task.setStatus(Status.CREATED);
        return task;
    }

    public static TaskResponse map(Task savedTask) {
        TaskResponse response = new TaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setPriority(savedTask.getPriority());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh-mm-ss").format(savedTask.getTimeCreated()));
        response.setTimeDone(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh-mm-ss").format(savedTask.getTimeDone()));
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        return response;
    }

    public static CreateTaskResponse mapTask(Task savedTask) {
        CreateTaskResponse response = new CreateTaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setPriority(savedTask.getPriority());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh-mm-ss").format(savedTask.getTimeCreated()));
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        return response;
    }
}
