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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semicolon.todo.utils.Mapper.map;

@Service
@AllArgsConstructor
public class TodoServicesImpl implements TodoServices{
    private final TodoRepository todoRepository;
    private final TaskServices taskServices;
    private final TaskRepository taskRepository;
    @Override
    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        validateRegistration(registerUserRequest);
        Todo todo = todoRepository.findByUsername(registerUserRequest.getUsername().toLowerCase());
        if (todo == null) {
            Todo newTodo = map(registerUserRequest);
            UserResponse response = map(newTodo);
            todoRepository.save(newTodo);
            return response;
        }
        throw new UserAlreadyExistException("User already exist");
    }

    private static void validateRegistration(RegisterUserRequest registerUserRequest) {
        if (!registerUserRequest.getUsername().matches("[a-zA-Z]+")) throw new UserAlreadyExistException("Invalid Input");
        if (registerUserRequest.getPassword().isEmpty())
            throw new IncorrectPassword("Invalid Password provide a Password");
    }

    @Override
    public UserResponse login(LoginUserRequest loginUserRequest) {
        validateLogin(loginUserRequest);
        Todo newTodo = todoRepository.findByUsername(loginUserRequest.getUsername().toLowerCase());
        UserResponse response = map(newTodo);
        newTodo.setLoggedIn(true);
        if (!newTodo.getPassword().equals(loginUserRequest.getPassword())) throw new IncorrectPassword("Incorrect password");
        todoRepository.save(newTodo);
        return response;
    }

    @Override
    public String logout(LogoutRequest logoutRequest) {
        Todo todo = todoRepository.findByUsername(logoutRequest.getUsername().toLowerCase());
        if (todo == null) throw new IncorrectPassword("Username is not valid");
        todo.setLoggedIn(false);
        todoRepository.save(todo);
        return "LoggedOut Successful";
    }

    @Override
    public Todo findByUser(String username) {
        return todoRepository.findByUsername(username.toLowerCase());
    }

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        Todo todo = todoRepository.findByUsername(createTaskRequest.getAuthor().toLowerCase());
        if(todo.getUsername().equals(createTaskRequest.getTitle())) throw new UserAlreadyExistException("Title already for user");
        todo.setLoggedIn(true);
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
    public CreateTaskResponse updateTask(UpdateTaskRequest updateTaskRequest) {
        Todo todo = todoRepository.findByUsername(updateTaskRequest.getAuthor().toLowerCase());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.updateTask(updateTaskRequest);
    }

    @Override
    public String deleteTask(DeleteTaskRequest deleteTaskRequest) {
        Todo todo = todoRepository.findByUsername(deleteTaskRequest.getAuthor().toLowerCase());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.deleteTask(deleteTaskRequest);
    }

    @Override
    public List<Task> getAllTask() {
        return taskServices.getAllTask();
    }
    @Override
    public List<Task> getAllStartedTask(String user) {
        Todo todo = todoRepository.findByUsername(user);
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getAllTaskStarted(user);
    }
    @Override
    public List<Task> getTaskFor(String user){
        Todo todo = todoRepository.findByUsername(user.toLowerCase());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getTaskFor(user.toLowerCase());
    }

    @Override
    public List<Task> getAllTaskCompleted(String user) {
        Todo todo = todoRepository.findByUsername(user);
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getAllTaskCompleted(user);
    }

    @Override
    public List<Task> getAllTaskCreated(String user) {
        Todo todo = todoRepository.findByUsername(user);
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getAllTaskCreated(user);
    }
    @Override
    public List<Task> getAllTaskInProgress(String user) {
        Todo todo = todoRepository.findByUsername(user);
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getAllTaskInProgress(user);
    }
    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToImportantRequest priority) {
        Todo todo = todoRepository.findByUsername(priority.getAuthor());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskPriorityTo(priority);
    }

    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToLessImportantRequest priority) {
        Todo todo = todoRepository.findByUsername(priority.getAuthor());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskPriorityTo(priority);
    }

    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToUrgentRequest priority) {
        Todo todo = todoRepository.findByUsername(priority.getAuthor());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskPriorityToUrg(priority);
    }
    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToLessUrgentRequest priority) {
        Todo todo = todoRepository.findByUsername(priority.getAuthor());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskPriorityToLessUrg(priority);
    }

    @Override
    public TaskResponse taskInProgress(TaskInProgressRequest inProgressRequest) {
        Todo todo = todoRepository.findByUsername(inProgressRequest.getAuthor());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskInProgress(inProgressRequest);
    }
    @Override
    public CreateTaskResponse startedTask(StartedTaskRequest startedTaskRequest){
        Todo todo = todoRepository.findByUsername(startedTaskRequest.getAuthor().toLowerCase());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.startedTask(startedTaskRequest);
    }

    @Override
    public TaskResponse taskCompleted(TaskCompletedRequest taskCompletedRequest) {
        Todo todo = todoRepository.findByUsername(taskCompletedRequest.getAuthor().toLowerCase());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskCompleted(taskCompletedRequest);
    }

    private static void validateLogin(LoginUserRequest loginUserRequest) {
        if (!loginUserRequest.getUsername().matches("[a-zA-Z]+")) throw new UserAlreadyExistException("Invalid Input");
        if (loginUserRequest.getPassword().isEmpty())
            throw new IncorrectPassword("Invalid Password provide a Password");
    }

}
