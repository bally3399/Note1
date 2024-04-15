package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.data.repositories.TaskRepository;
import africa.semicolon.todo.data.repositories.TodoRepository;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.dtos.response.UserResponse;
import africa.semicolon.todo.exceptions.IncorrectPassword;
import africa.semicolon.todo.exceptions.TaskNotFoundException;
import africa.semicolon.todo.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;

import static africa.semicolon.todo.utils.Mapper.map;

@Service
public class TodoServicesImpl implements TodoServices{
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TaskServices taskServices;
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        validateRegistration(registerUserRequest);
        Todo todo = todoRepository.findByUsername(registerUserRequest.getUsername());
        if (todo == null) {
            Todo newTodo = map(registerUserRequest);
            UserResponse response = map(newTodo);
            todoRepository.save(newTodo);
            return response;
        }
        throw new UserAlreadyExistException("User already exist");
    }

    private static void validateRegistration(RegisterUserRequest registerUserRequest) {
        if (!registerUserRequest.getUsername().matches("[a-zA-Z]+")) throw new InputMismatchException("Invalid Input");
        if (registerUserRequest.getPassword().isEmpty())
            throw new InputMismatchException("Invalid Password provide a Password");
    }

    @Override
    public UserResponse login(LoginUserRequest loginUserRequest) {
        validateLogin(loginUserRequest);
        Todo newTodo = todoRepository.findByUsername(loginUserRequest.getUsername());
        UserResponse response = map(newTodo);
        newTodo.setLoggedIn(true);
        if (!newTodo.getPassword().equals(loginUserRequest.getPassword())) throw new IncorrectPassword("Incorrect password");
        todoRepository.save(newTodo);
        return response;
    }

    @Override
    public String logout(LogoutRequest logoutRequest) {
        Todo todo = todoRepository.findByUsername(logoutRequest.getUsername());
        if (todo == null) throw new IncorrectPassword("Username is not valid");
        todo.setLoggedIn(false);
        return "LoggedOut Successful";
    }

    @Override
    public Todo findByUser(String username) {
        return todoRepository.findByUsername(username);
    }

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        Todo todo = todoRepository.findByUsername(createTaskRequest.getAuthor().toLowerCase());
        if(todo != null) todo.setLoggedIn(true);
        return taskServices.createTask(createTaskRequest);
    }

    @Override
    public Task findTaskByTitle(String title) {
        for (Task task : taskRepository.findAll()) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        throw new TaskNotFoundException("not found");
    }

    @Override
    public CreateTaskResponse updateTask(UpdateTaskRequest updateNoteRequest) {
        return taskServices.updateTask(updateNoteRequest);
    }

    @Override
    public String deleteTask(CreateTaskRequest createTaskRequest) {
        return taskServices.deleteTask(createTaskRequest);
    }

    @Override
    public List<Task> getAllTask() {
        return taskServices.getAllTask();
    }
    @Override
    public List<Task> getAllStartedTask() {
        return taskServices.getAllTaskStarted();
    }

    @Override
    public List<Task> getAllTaskCompleted() {
        return taskServices.getAllTaskCompleted();
    }

    @Override
    public List<Task> getAllTaskCreated() {
        return taskServices.getAllTaskCreated();
    }
    @Override
    public List<Task> getAllTaskInProgress() {
        return taskServices.getAllTaskInProgress();
    }


    @Override
    public TaskResponse taskInProgress(TaskInProgressRequest inProgressRequest) {
        return taskServices.taskInProgress(inProgressRequest);
    }
    @Override
    public CreateTaskResponse startedTask(StartedTaskRequest startedTaskRequest){
        return taskServices.startedTask(startedTaskRequest);
    }

    @Override
    public TaskResponse taskCompleted(TaskCompletedRequest taskCompletedRequest) {
        return taskServices.taskCompleted(taskCompletedRequest);
    }

    private static void validateLogin(LoginUserRequest loginUserRequest) {
        if (!loginUserRequest.getUsername().matches("[a-zA-Z]+")) throw new InputMismatchException("Invalid Input");
        if (loginUserRequest.getPassword().isEmpty())
            throw new InputMismatchException("Invalid Password provide a Password");
    }

}
