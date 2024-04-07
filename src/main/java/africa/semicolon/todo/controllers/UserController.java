package africa.semicolon.todo.controllers;

import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.TaskApiResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.dtos.response.UserApiResponse;
import africa.semicolon.todo.dtos.response.UserResponse;
import africa.semicolon.todo.exceptions.TodoExceptions;
import africa.semicolon.todo.services.TodoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.InputMismatchException;

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
            return new ResponseEntity<>(new UserApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest) {
        try {
            UserResponse result = todoServices.login(loginUserRequest);
            return new ResponseEntity<>(new UserApiResponse(true, result), CREATED);
        } catch (TodoExceptions | InputMismatchException e) {
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        try {
            UserResponse result = todoServices.logout(logoutRequest);
            return new ResponseEntity<>(new UserApiResponse(true, result), CREATED);
        } catch (TodoExceptions e) {
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody CreateTaskRequest taskRequest){
        try{
            TaskResponse result = todoServices.createTask(taskRequest);
            return new ResponseEntity<>(new TaskApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new TaskApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<?> UpdateNote(@RequestBody UpdateTaskRequest updateRequest){
        try{
            TaskResponse result = todoServices.updateNote(updateRequest);
            return new ResponseEntity<>(new TaskApiResponse(true, result), CREATED);
        }catch (TodoExceptions | InputMismatchException e){
            return new ResponseEntity<>(new TaskApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> DeleteNote(@RequestBody CreateTaskRequest deleteNoteRequest){
        try{
            String result = todoServices.deleteTask(deleteNoteRequest);
            return new ResponseEntity<>(new TaskApiResponse(true, result), CREATED);
        }catch (TodoExceptions |InputMismatchException e){
            return new ResponseEntity<>(new TaskApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
}