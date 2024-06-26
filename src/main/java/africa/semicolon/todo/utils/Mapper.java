package africa.semicolon.todo.utils;

import africa.semicolon.todo.data.model.Notification;
import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.User;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static User map(RegisterUserRequest registerUserRequest) {
        User todo = new User();
        todo.setUsername(registerUserRequest.getUsername().toLowerCase());
        todo.setPassword(registerUserRequest.getPassword());
        return todo;
    }

    public static UserResponse map(User savedTodo) {
        UserResponse response = new UserResponse();
        response.setUsername(savedTodo.getUsername());
        response.setMessage("Successfully registered");
        return response;
    }

    public static LoginUserResponse mapLogin(User savedTodo) {
        LoginUserResponse response = new LoginUserResponse();
        response.setMessage("Successful");
        response.setUsername(savedTodo.getUsername());
        List<Notification> notifications =  savedTodo.getNotifications();
        List<Notification> newNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if(!notification.isSeen()){
                newNotifications.add(notification);
            }
        }
        response.setNotificationList(newNotifications);
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
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeCreated()));
        response.setTimeStarted(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeStarted()));
        response.setTimeInProgress(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeInProgress()));
        response.setTimeDone(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeDone()));
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        return response;
    }

    public static CreateTaskResponse mapTask(Task savedTask) {
        CreateTaskResponse response = new CreateTaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setPriority(savedTask.getPriority());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeCreated()));
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        response.setId(savedTask.getId());
        return response;
    }
    public static StartedTaskResponse mapStartedTask(Task savedTask) {
        StartedTaskResponse response = new StartedTaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setPriority(savedTask.getPriority());
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeCreated()));
        response.setTimeStarted(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeStarted()));
        return response;
    }
    public static TaskInProgressResponse mapTaskInProgress(Task savedTask) {
        TaskInProgressResponse response = new TaskInProgressResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setPriority(savedTask.getPriority());
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        response.setTimeStarted(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeStarted()));
        response.setTimeInProgress(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(LocalDateTime.now()));
        return response;
    }
    public static UpdateTaskResponse mapUpdateTask(Task savedTask) {
        UpdateTaskResponse response = new UpdateTaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setPriority(savedTask.getPriority());
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeCreated()));
        response.setTimeUpdated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(LocalDateTime.now()));
        return response;
    }
    public static TaskDoneResponse mapTaskDone(Task savedTask) {
        TaskDoneResponse response = new TaskDoneResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setPriority(savedTask.getPriority());
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeCreated()));
        response.setTimeDone(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(LocalDateTime.now()));
        return response;
    }
    public static AssignTaskResponse mapAssignTask(Task savedTask) {
        AssignTaskResponse response = new AssignTaskResponse();
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setPriority(savedTask.getPriority());
        response.setAuthor(savedTask.getAuthor());
        response.setStatus(savedTask.getStatus());
        response.setTimeCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(savedTask.getTimeCreated()));
        response.setId(savedTask.getId());
        return response;
    }
    public static Task mapAssign(AssignTaskRequest assignTaskRequest){
        Task task = new Task();
        task.setTitle(assignTaskRequest.getTitle());
        task.setAuthor(assignTaskRequest.getAuthor());
        task.setDescription(assignTaskRequest.getDescription());
        task.setPriority(assignTaskRequest.getPriority());
        task.setStatus(assignTaskRequest.getStatus());
        return task;
    }

    public static Notification map(NotificationRequest notificationRequest){
        Notification notification = new Notification();
        notification.setReceiver(notificationRequest.getUsername());
        notification.setMessage(notificationRequest.getMessage());
        notification.setTaskId(notificationRequest.getTaskId());
        notification.setTimestamp(LocalDateTime.now());
        return notification;
    }
    public static NotificationResponse mapNotification(Notification notification){
        NotificationResponse response = new NotificationResponse();
        response.setTaskId(notification.getTaskId());
        response.setMessage(notification.getMessage());
        response.setUsername(notification.getReceiver());
        response.setTimeStamp(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(notification.getTimestamp()));
        response.setNotificationId(notification.getId());
        return response;
    }

    public static Notification mapView(ViewNotificationRequest request){
        Notification notification = new Notification();
        notification.setId(request.getNotificationId());
        notification.setReceiver(request.getUsername());
        return notification;
    }
    public static ViewNotificationResponse mapViewRes(Notification newNotification){
        ViewNotificationResponse response = new ViewNotificationResponse();
        response.setNotificationId(newNotification.getId());
//        response.setTaskId(newNotification.getTaskId());
        response.setMessage("Viewed");
        response.setUsername(newNotification.getReceiver());
        response.setTimeStamp(LocalDateTime.now());
        return response;
    }
}
