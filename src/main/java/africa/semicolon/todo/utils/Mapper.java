package africa.semicolon.todo.utils;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.dtos.request.CreateTaskRequest;
import africa.semicolon.todo.dtos.request.LoginUserRequest;
import africa.semicolon.todo.dtos.request.RegisterUserRequest;
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
        response.setUsername(savedTodo.getUsername());
        response.setPassword(savedTodo.getPassword());
        response.setMessage("Successful");
        return response;
    }
    public static Todo map(LoginUserRequest loginUserRequest){
        Todo todo = new Todo();
        todo.setPassword(loginUserRequest.getPassword());
        todo.setUsername(loginUserRequest.getUsername());
        return todo;
    }
    public static Task map(CreateTaskRequest createTaskRequest){
        Task task = new Task();
        task.setTitle(createTaskRequest.getTitle());
        task.setDescription(createTaskRequest.getDescription());
        task.setTimeCreated(LocalDateTime.now());
        task.setAuthor(createTaskRequest.getAuthor());
        task.setId(task.getId());
        return task;
    }
    public static TaskResponse map(Task savedTask){
        TaskResponse response = new TaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setTimeCreated(savedTask.getTimeCreated());
        response.setTimeDone(savedTask.getTimeDone());
        response.setAuthor(savedTask.getAuthor());
        return response;
    }
}
