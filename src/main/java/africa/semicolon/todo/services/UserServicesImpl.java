package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.User;
import africa.semicolon.todo.data.repositories.TaskRepository;
import africa.semicolon.todo.data.repositories.UserRepository;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.*;
import africa.semicolon.todo.exceptions.IncorrectPassword;
import africa.semicolon.todo.exceptions.TaskNotFoundException;
import africa.semicolon.todo.exceptions.UserAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semicolon.todo.utils.Mapper.*;

@Service
@AllArgsConstructor
 public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final TaskServices taskServices;
    private final TaskRepository taskRepository;
    @Override
    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        String username = registerUserRequest.getUsername().toLowerCase();
        validate(username);
        validateRegistration(registerUserRequest);
        User newUser = map(registerUserRequest);
        UserResponse response = map(newUser);
        userRepository.save(newUser);
        return response;
    }

    private void validate(String username) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(username.toLowerCase())) {
                throw new UserAlreadyExistException("Username already exist");
            }
        }
    }

    private static void validateRegistration(RegisterUserRequest registerUserRequest) {
        if (!registerUserRequest.getUsername().matches("[a-zA-Z]+")) throw new UserAlreadyExistException("Invalid Input");
        if (registerUserRequest.getPassword().isEmpty())
            throw new IncorrectPassword("Invalid Password provide a Password");
    }

    @Override
    public LoginUserResponse login(LoginUserRequest loginUserRequest) {
        validateLogin(loginUserRequest);
        User newTodo = userRepository.findByUsername(loginUserRequest.getUsername().toLowerCase());
        LoginUserResponse response = mapLogin(newTodo);
        newTodo.setLoggedIn(true);
        if (!newTodo.getPassword().equals(loginUserRequest.getPassword())) throw new IncorrectPassword("Incorrect password");
        userRepository.save(newTodo);
        return response;
    }

    @Override
    public String logout(LogoutRequest logoutRequest) {
        User user = userRepository.findByUsername(logoutRequest.getUsername().toLowerCase());
        if (user == null) throw new IncorrectPassword("Username is not valid");
        user.setLoggedIn(false);
        userRepository.save(user);
        return "LoggedOut Successful";
    }

    @Override
    public User findByUser(String username) {
        return userRepository.findByUsername(username.toLowerCase());
    }

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        User user = userRepository.findByUsername(createTaskRequest.getAuthor().toLowerCase());
        if(user == null){
            throw new UserAlreadyExistException("user does not exist");
        }
        if(!user.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        user.setLoggedIn(true);
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
    public UpdateTaskResponse updateTask(UpdateTaskRequest updateTaskRequest) {
        User user = userRepository.findByUsername(updateTaskRequest.getAuthor().toLowerCase());
        if(!user.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.updateTask(updateTaskRequest);
    }

    @Override
    public String deleteTask(DeleteTaskRequest deleteTaskRequest) {
        User user = userRepository.findByUsername(deleteTaskRequest.getAuthor().toLowerCase());
        if(!user.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.deleteTask(deleteTaskRequest);
    }

    @Override
    public List<Task> getAllTask() {
        return taskServices.getAllTask();
    }
    @Override
    public List<Task> getAllStartedTask(String user) {
        User user1 = userRepository.findByUsername(user);
        if(!user1.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getAllTaskStarted(user);
    }
    @Override
    public List<Task> getTaskFor(String user){
        User todo = userRepository.findByUsername(user.toLowerCase());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return todo.getTasks();
    }

    @Override
    public List<Task> getAllTaskCompleted(String user) {
        User user1 = userRepository.findByUsername(user);
        if(!user1.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getAllTaskCompleted(user);
    }

    @Override
    public List<Task> getAllTaskCreated(String user) {
        User user1 = userRepository.findByUsername(user);
        if(!user1.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getAllTaskCreated(user);
    }
    @Override
    public List<Task> getAllTaskInProgress(String user) {
        User todo = userRepository.findByUsername(user);
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.getAllTaskInProgress(user);
    }
    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToImportantRequest priority) {
        User user = userRepository.findByUsername(priority.getAuthor());
        if(!user.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskPriorityTo(priority);
    }

    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToLessImportantRequest priority) {
        User user = userRepository.findByUsername(priority.getAuthor());
        if(!user.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskPriorityTo(priority);
    }

    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToUrgentRequest priority) {
        User todo = userRepository.findByUsername(priority.getAuthor());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskPriorityToUrg(priority);
    }
    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToLessUrgentRequest priority) {
        User todo = userRepository.findByUsername(priority.getAuthor());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskPriorityToLessUrg(priority);
    }

    @Override
    public TaskInProgressResponse taskInProgress(TaskInProgressRequest inProgressRequest) {
        User user = userRepository.findByUsername(inProgressRequest.getAuthor());
        if(user == null){
            throw new UserAlreadyExistException("user not exist");
        }
        if(!user.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskInProgress(inProgressRequest);
    }
    @Override
    public StartedTaskResponse startedTask(StartedTaskRequest startedTaskRequest){
        User todo = userRepository.findByUsername(startedTaskRequest.getAuthor().toLowerCase());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.startedTask(startedTaskRequest);
    }

    @Override
    public TaskDoneResponse taskCompleted(TaskCompletedRequest taskCompletedRequest) {
        User todo = userRepository.findByUsername(taskCompletedRequest.getAuthor().toLowerCase());
        if(!todo.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");
        return taskServices.taskCompleted(taskCompletedRequest);
    }

    @Override
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest) {
        User user = findByUser(assignTaskRequest.getAuthor());
        if(user == null) throw new TaskNotFoundException(assignTaskRequest.getAuthor()+ " Not found");
        if(!user.isLoggedIn()) throw new UserAlreadyExistException("user must be loggedIn");

        var response = taskServices.assignTask(assignTaskRequest);
        User user1 = findByUser(assignTaskRequest.getUser());
        if(user1 == null) throw new TaskNotFoundException(assignTaskRequest.getUser() +" Not found");
        Task task = taskServices.findTaskById(response.getId());
        List<Task> tasks = user1.getTasks();
        tasks.add(task);
        user1.setTasks(tasks);
        userRepository.save(user1);
        return mapAssignTask(task);
    }

    private static void validateLogin(LoginUserRequest loginUserRequest) {
        if (!loginUserRequest.getUsername().matches("[a-zA-Z]+")) throw new UserAlreadyExistException("Invalid Input");
        if (loginUserRequest.getPassword().isEmpty())
            throw new IncorrectPassword("Invalid Password provide a Password");
    }

}
