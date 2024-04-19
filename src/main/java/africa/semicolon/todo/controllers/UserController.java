package africa.semicolon.todo.controllers;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.dtos.response.ApiResponse;
import africa.semicolon.todo.dtos.response.UserResponse;
import africa.semicolon.todo.exceptions.TodoExceptions;
import africa.semicolon.todo.services.TodoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/Todo")
public class UserController {
    @Autowired
    private TodoServices todoServices;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest){
        try{
            UserResponse result = todoServices.registerUser(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest) {
        try {
            UserResponse result = todoServices.login(loginUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (TodoExceptions | InputMismatchException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        try {
            String result = todoServices.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (TodoExceptions e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody CreateTaskRequest taskRequest){
        try{
            CreateTaskResponse result = todoServices.createTask(taskRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/started")
    public ResponseEntity<?> StartedTask(@RequestBody StartedTaskRequest taskRequest){
        try{
            CreateTaskResponse result = todoServices.startedTask(taskRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/inProgress")
    public ResponseEntity<?> taskInProgress(@RequestBody TaskInProgressRequest inProgressRequest){
        try{
            TaskResponse result = todoServices.taskInProgress(inProgressRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/completed")
    public ResponseEntity<?> taskCompleted(@RequestBody TaskCompletedRequest taskCompletedRequest){
        try{
            TaskResponse result = todoServices.taskCompleted(taskCompletedRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<?> UpdateTask(@RequestBody UpdateTaskRequest updateRequest){
        try{
            CreateTaskResponse result = todoServices.updateTask(updateRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/changePriority")
    public ResponseEntity<?> taskPriorityTo(@RequestBody TaskPriorityToImportantRequest priority){
        try{
            TaskResponse result = todoServices.taskPriorityTo(priority);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/changePriority1")
    public ResponseEntity<?> taskPriorityTo(@RequestBody TaskPriorityToLessImportantRequest priority){
        try{
            TaskResponse result = todoServices.taskPriorityTo(priority);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/changePriority2")
    public ResponseEntity<?> taskPriorityTo(@RequestBody TaskPriorityToUrgentRequest priority){
        try{
            TaskResponse result = todoServices.taskPriorityTo(priority);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/changePriority3")
    public ResponseEntity<?> taskPriorityTo(@RequestBody TaskPriorityToLessUrgentRequest priority){
        try{
            TaskResponse result = todoServices.taskPriorityTo(priority);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> DeleteNote(@RequestBody DeleteTaskRequest deleteTaskRequest){
        try{
            String result = todoServices.deleteTask(deleteTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions |InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @GetMapping("/get")
    public ResponseEntity<?> getAllTask(){
        try{
            List<Task> result = todoServices.getAllTask();
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/getTask")
    public ResponseEntity<?> getAllTaskStarted(@RequestBody GetTaskRequest getTaskRequest){
        try{
            List<Task> result = todoServices.getAllStartedTask(getTaskRequest.getUsername());
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }


    @GetMapping("/getTaskCreated")
    public ResponseEntity<?> getAllTaskCreated(@RequestBody GetTaskRequest getTaskRequest){
        try{
            List<Task> result = todoServices.getAllTaskCreated(getTaskRequest.getUsername());
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/getTaskInProgress")
    public ResponseEntity<?> getAllTaskInProgress(@RequestBody GetTaskRequest getTaskRequest){
        try{
            List<Task> result = todoServices.getAllTaskInProgress(getTaskRequest.getUsername());
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/getTaskDone")
    public ResponseEntity<?> getAllTaskCompleted(@RequestBody GetTaskRequest getTaskRequest){
        try{
            List<Task> result = todoServices.getAllTaskCompleted(getTaskRequest.getUsername());
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping ("/getTaskFor")
    public ResponseEntity<?> getTaskFor(@RequestBody GetTaskRequest getTaskRequest){
        try{
            List<Task> result = todoServices.getTaskFor(getTaskRequest.getUsername());
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
}