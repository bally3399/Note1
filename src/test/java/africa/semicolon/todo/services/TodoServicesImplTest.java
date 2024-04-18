package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.repositories.TodoRepository;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TodoServicesImplTest {
    @Autowired
    private TodoServices todoServices;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TaskServices taskServices;
    @BeforeEach
    public void setUp(){
        todoRepository.deleteAll();
        taskServices.deleteAll();
    }
    @Test
    public void registerUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);
        assertEquals(1, todoRepository.count());

    }

    @Test
    public void registerTwoUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("bally");
        registerUserRequest1.setPassword("Password");
        todoServices.registerUser(registerUserRequest1);
        assertEquals(2, todoRepository.count());

    }

    @Test
    public void registerUser_login(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("username");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);
        assertEquals(1, todoRepository.count());
    }

    @Test
    public void createTodoWithSameTitleTest_ThrowTitleAlreadyExist(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");

        todoServices.registerUser(registerUserRequest);

        assertThrows(UserAlreadyExistException.class,() -> todoServices.registerUser(registerUserRequest));
    }

    @Test
    public void testWhenUserInputEmptyStringAsUserName_throwInputMismatchException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("");
        registerUserRequest.setPassword("password");
        try {
            todoServices.registerUser(registerUserRequest);
        }catch(InputMismatchException e){
            assertEquals(e.getMessage(), "Invalid Input");
        }

    }

    @Test
    public void testWhenUserInputEmptyStringAsPassword_throwInputMismatchException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("bally");
        registerUserRequest.setPassword("");
        try {
            todoServices.registerUser(registerUserRequest);
        }catch(InputMismatchException e){
            assertEquals(e.getMessage(), "Invalid Password provide a Password");
        }
    }

    @Test
    public void testWhenUserInputInvalidName_throwInputMismatchException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("B a l l y");
        registerUserRequest.setPassword("password");
        try {
            todoServices.registerUser(registerUserRequest);
        }catch(InputMismatchException e){
            assertEquals(e.getMessage(), "Invalid Input");
        }
    }

    @Test
    public void testWhenUserInputInvalidPassword_throwInputMismatchException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("p a s s w o r d");
        try {
            todoServices.registerUser(registerUserRequest);
        }catch(InputMismatchException e){
            assertEquals(e.getMessage(), "Invalid Password provide a Password");
        }
    }

    @Test
    public void registerUserFindUserByUsernameTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);
        assertEquals("bally", todoServices.findByUser("bally").getUsername());
    }

    @Test
    public void registerUser_login_logoutTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("Bally");
        todoServices.logout(logoutRequest);

        assertFalse(todoServices.findByUser("Bally").isLoggedIn());

    }


    @Test
    public void registerUser_login_createTaskTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("title");
        createTaskRequest.setDescription(Level.IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        CreateTaskResponse task = todoServices.createTask(createTaskRequest);
        assertEquals("title", task.getTitle());
        assertEquals(Level.IMPORTANT, task.getDescription());
        assertEquals(1, taskServices.getTaskFor("Bally").size());


    }

    @Test
    public void registerUser_login_createNote_findTaskByAuthor(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("title");
        createTaskRequest.setDescription(Level.LESS_IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        todoServices.createTask(createTaskRequest);
        assertEquals(todoServices.findTaskByTitle("title").getTitle(),"title");
    }

    @Test
    public void registerUser_login_createTask_updateTaskTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("title");
        createTaskRequest.setDescription(Level.LESS_IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        todoServices.createTask(createTaskRequest);

        UpdateTaskRequest updateNoteRequest = new UpdateTaskRequest();
        updateNoteRequest.setTitle("title");
        updateNoteRequest.setNewTitle("newTitle");
        updateNoteRequest.setDescription(Level.LESS_IMPORTANT);
        updateNoteRequest.setNewDescription(Level.IMPORTANT);
        updateNoteRequest.setAuthor("Bally");

        CreateTaskResponse task = todoServices.updateTask(updateNoteRequest);
        assertEquals("newTitle", task.getTitle());
        assertEquals(Level.IMPORTANT, task.getDescription());
        assertEquals(1, taskServices.getTaskFor("Bally").size());
    }

    @Test
    public void registerUser_login_createNote_deleteNote(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("title");
        createTaskRequest.setDescription(Level.LESS_URGENT);
        createTaskRequest.setAuthor("Bally");
        todoServices.createTask(createTaskRequest);



        todoServices.deleteTask(createTaskRequest);
        assertEquals(0, taskServices.getAllTask().size());
    }
    @Test
    public void testWhenUSerInputEmptyStringAsTitleOfTask_throwInputMismatchException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("");
        createTaskRequest.setDescription(Level.LESS_URGENT);
        createTaskRequest.setAuthor("Bally");
        try {
            todoServices.createTask(createTaskRequest);
        }catch(InputMismatchException e){
            assertEquals(e.getMessage(), "Title not found");
        }
    }

    @Test
    public void testWhenUserInputEmptyStringAsAuthorOfTask_throwInputMismatchException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("title");
        createTaskRequest.setDescription(Level.URGENT);
        createTaskRequest.setAuthor("");
        try {
            todoServices.createTask(createTaskRequest);
        }catch(InputMismatchException e){
            assertEquals(e.getMessage(), "Invalid Input");
        }
    }
    @Test
    public void testThatWhenUserEnterIntegerAsUsername_throwInputMismatchException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("1 2 3 4 5");
        loginUserRequest.setPassword("password");
        try {
            todoServices.login(loginUserRequest);
        }catch(InputMismatchException e){
            assertEquals(e.getMessage(),"Invalid Input");
        }
    }
    @Test
    public void registerUser_login_createTask_taskInProgress() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("title");
        createTaskRequest.setDescription(Level.LESS_IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        todoServices.createTask(createTaskRequest);

        TaskInProgressRequest inProgressRequest = new TaskInProgressRequest();
        inProgressRequest.setTitle("title");
        inProgressRequest.setDescription(Level.LESS_IMPORTANT);
        inProgressRequest.setAuthor("Bally");
        TaskResponse task = todoServices.taskInProgress(inProgressRequest);

        assertEquals(Level.LESS_IMPORTANT, task.getDescription());
        assertEquals(Status.IN_PROGRESS, task.getStatus());
        assertEquals(1, taskServices.getTaskFor("Bally").size());
    }

    @Test
    public void registerUser_login_createTask_taskInProgress_taskDone() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("title");
        createTaskRequest.setDescription(Level.LESS_IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        todoServices.createTask(createTaskRequest);

        StartedTaskRequest startedTaskRequest = new StartedTaskRequest();
        startedTaskRequest.setTitle("title");
        startedTaskRequest.setDescription(Level.LESS_IMPORTANT);
        startedTaskRequest.setAuthor("Bally");
        todoServices.startedTask(startedTaskRequest);

        TaskInProgressRequest inProgressRequest = new TaskInProgressRequest();
        inProgressRequest.setTitle("title");
        inProgressRequest.setDescription(Level.LESS_IMPORTANT);
        inProgressRequest.setAuthor("Bally");
        todoServices.taskInProgress(inProgressRequest);

        TaskCompletedRequest taskCompletedRequest = new TaskCompletedRequest();
        taskCompletedRequest.setTitle("title");
        taskCompletedRequest.setDescription(Level.LESS_IMPORTANT);
        taskCompletedRequest.setAuthor("Bally");
        TaskResponse task = todoServices.taskCompleted(taskCompletedRequest);
        assertEquals(Level.LESS_IMPORTANT, task.getDescription());
        assertEquals(Status.COMPLETED, task.getStatus());
        assertEquals(1, taskServices.getTaskFor("Bally").size());
    }

}