package africa.semicolon.todo.controllers;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.dtos.request.*;
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
            TaskResponse result = todoServices.createTask(taskRequest);
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
            TaskResponse result = todoServices.updateTask(updateRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> DeleteNote(@RequestBody CreateTaskRequest deleteNoteRequest){
        try{
            String result = todoServices.deleteTask(deleteNoteRequest);
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
}