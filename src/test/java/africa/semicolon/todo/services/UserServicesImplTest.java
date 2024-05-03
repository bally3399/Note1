package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.repositories.UserRepository;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.*;
import africa.semicolon.todo.exceptions.IncorrectPassword;
import africa.semicolon.todo.exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServicesImplTest {
    @Autowired
    private UserServices todoServices;
    @Autowired
    private UserRepository todoRepository;
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
        }catch(UserAlreadyExistException e){
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
        }catch(IncorrectPassword e){
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
        }catch(UserAlreadyExistException e){
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
        createTaskRequest.setPriority(Level.IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        CreateTaskResponse task = todoServices.createTask(createTaskRequest);
        assertEquals("title", task.getTitle());
        assertEquals(Level.IMPORTANT, task.getPriority());
        assertEquals(1, taskServices.getTaskFor("bally").size());


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
        createTaskRequest.setPriority(Level.LESS_IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        createTaskRequest.setDescription("description");
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
        createTaskRequest.setPriority(Level.LESS_IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        createTaskRequest.setDescription("Buy food");
        todoServices.createTask(createTaskRequest);

        UpdateTaskRequest updateNoteRequest = new UpdateTaskRequest();
        updateNoteRequest.setTitle("title");
        updateNoteRequest.setNewTitle("newTitle");
        updateNoteRequest.setAuthor("Bally");
        updateNoteRequest.setDescription("Buy food");

        UpdateTaskResponse task = todoServices.updateTask(updateNoteRequest);
        assertEquals("newTitle", task.getTitle());
        assertEquals(Level.LESS_IMPORTANT, task.getPriority());
        assertEquals("Buy food", task.getDescription());
        assertEquals(1, taskServices.getTaskFor("Bally").size());
    }

    @Test
    public void registerUser_login_createTask_deleteTask(){
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
        createTaskRequest.setPriority(Level.LESS_URGENT);
        createTaskRequest.setAuthor("Bally");
        createTaskRequest.setDescription("Buy food");
        CreateTaskResponse response = todoServices.createTask(createTaskRequest);

        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setId(response.getId());
        deleteTaskRequest.setAuthor("Bally");
        todoServices.deleteTask(deleteTaskRequest);
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
        createTaskRequest.setPriority(Level.LESS_URGENT);
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
        createTaskRequest.setPriority(Level.URGENT);
        createTaskRequest.setAuthor("");
        try {
            todoServices.createTask(createTaskRequest);
        }catch(UserAlreadyExistException e){
            assertEquals(e.getMessage(), "user does not exist");
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
        }catch(UserAlreadyExistException e){
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
        createTaskRequest.setPriority(Level.LESS_IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        createTaskRequest.setDescription("Buy food");
        CreateTaskResponse response = todoServices.createTask(createTaskRequest);

        TaskInProgressRequest inProgressRequest = new TaskInProgressRequest();
        inProgressRequest.setId(response.getId());
        inProgressRequest.setAuthor("Bally");
        try {
            todoServices.taskInProgress(inProgressRequest);
        }catch(UserAlreadyExistException e){
            assertEquals(e.getMessage(), "user not exist");
        }
//        assertEquals(Level.LESS_IMPORTANT, task.getPriority());
//        assertEquals(Status.IN_PROGRESS, task.getStatus());
//        assertEquals(1, taskServices.getTaskFor("Bally").size());
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
        createTaskRequest.setPriority(Level.LESS_IMPORTANT);
        createTaskRequest.setAuthor("Bally");
        CreateTaskResponse response = todoServices.createTask(createTaskRequest);

        StartedTaskRequest startedTaskRequest = new StartedTaskRequest();
        startedTaskRequest.setId(response.getId());
        startedTaskRequest.setId(response.getId());
        startedTaskRequest.setAuthor("Bally");
        todoServices.startedTask(startedTaskRequest);

        TaskInProgressRequest inProgressRequest = new TaskInProgressRequest();
        inProgressRequest.setId(response.getId());
        inProgressRequest.setAuthor("Bally");
        todoServices.taskInProgress(inProgressRequest);

        TaskCompletedRequest taskCompletedRequest = new TaskCompletedRequest();
        taskCompletedRequest.setId(response.getId());
        taskCompletedRequest.setId(response.getId());
        taskCompletedRequest.setAuthor("Bally");
        TaskDoneResponse task = todoServices.taskCompleted(taskCompletedRequest);
        assertEquals(Level.LESS_IMPORTANT, task.getPriority());
        assertEquals(Status.COMPLETED, task.getStatus());
        assertEquals(1, taskServices.getTaskFor("Bally").size());
    }

    @Test
    public void register_loginUser_assignTaskTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Bally");
        registerUserRequest.setPassword("password");
        var response = todoServices.registerUser(registerUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("Bally");
        loginUserRequest.setPassword("password");
        todoServices.login(loginUserRequest);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("user");
        registerUserRequest2.setPassword("password");
        var response1 = todoServices.registerUser(registerUserRequest2);

        LoginUserRequest loginUserRequest2 = new LoginUserRequest();
        loginUserRequest2.setUsername("user");
        loginUserRequest2.setPassword("password");
        todoServices.login(loginUserRequest2);

        AssignTaskRequest assignTaskRequest = new AssignTaskRequest();
        assignTaskRequest.setTitle("title");
        assignTaskRequest.setPriority(Level.LESS_IMPORTANT);
        assignTaskRequest.setAuthor("Bally");
        assignTaskRequest.setDescription("Buy food");
        assignTaskRequest.setUser("user");
        todoServices.assignTask(assignTaskRequest);

        System.out.println(response);
        System.out.println(response1);
    }

}