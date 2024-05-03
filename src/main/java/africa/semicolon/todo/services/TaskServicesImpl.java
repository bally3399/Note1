package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.repositories.TaskRepository;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.*;
import africa.semicolon.todo.exceptions.TaskNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import static africa.semicolon.todo.utils.Mapper.*;

@Service
@AllArgsConstructor
public class TaskServicesImpl implements TaskServices{
    private final TaskRepository taskRepository;
    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        validateTask(createTaskRequest);
        for(Task tasks : taskRepository.findByAuthor(createTaskRequest.getAuthor().toLowerCase())){
            if(tasks.getTitle().equalsIgnoreCase(createTaskRequest.getTitle())) throw new TaskNotFoundException("Task Already Exist");
        }
        Task task = map(createTaskRequest);
        task.setStatus(Status.CREATED);
        taskRepository.save(task);
        return mapTask(task);
    }

    @Override
    public UpdateTaskResponse updateTask(UpdateTaskRequest task) {
        validateUpdate(task);
        Task updateTask = taskRepository.findByTitle(task.getTitle());
        if (task.getTitle()!= null && task.getAuthor() != null){
            updateTask.setTitle(task.getNewTitle());
            updateTask.setAuthor(task.getAuthor());
            updateTask.setDescription(task.getDescription());
        }
        Task savedTask = taskRepository.save(updateTask);
        return mapUpdateTask(savedTask);
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }
    @Override
    public List<Task> getAllTaskStarted(String user){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findByAuthor(user)){
            if(Status.STARTED == task.getStatus() && task.getAuthor().equals(user)) tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List<Task> getAllTaskCreated(String user){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.CREATED == task.getStatus() && task.getAuthor().equals(user)) tasks.add(task);
        }
        return tasks;
    }
    @Override
    public List<Task> getAllTaskInProgress(String user){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.IN_PROGRESS == task.getStatus() && task.getAuthor().equals(user)) tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List<Task> getAllTaskCompleted(String user){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.COMPLETED == task.getStatus() && task.getAuthor().equals(user)) tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List<Task> getTaskFor(String username) {
        List<Task> UserTask = taskRepository.findByAuthor(username);
        if(UserTask.isEmpty()) throw new TaskNotFoundException("Task not found");
        return UserTask;
    }

    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
    }

    @Override
    public String deleteTask(DeleteTaskRequest deleteTaskRequest) {
        if(deleteTaskRequest.getId().trim().isEmpty())throw new InputMismatchException("Title not found");
        Optional<Task> foundTask = taskRepository.findById(deleteTaskRequest.getId());
        taskRepository.delete(foundTask.get());
        return "Task Deleted Successfully";
    }

    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToImportantRequest priority) {
        if(priority.getId().trim().isEmpty())throw new InputMismatchException("id not found");
        Optional<Task> foundTask =  taskRepository.findById(priority.getId());
        foundTask.get().setPriority(Level.IMPORTANT);
        taskRepository.save(foundTask.get());
        return map(foundTask.get());
    }

    @Override
    public TaskResponse taskPriorityTo(TaskPriorityToLessImportantRequest priority) {
        if(priority.getId().trim().isEmpty())throw new InputMismatchException("id not found");
        Optional<Task> foundTask =  taskRepository.findById(priority.getId());
        foundTask.get().setPriority(Level.LESS_IMPORTANT);
        taskRepository.save(foundTask.get());
        return map(foundTask.get());
    }

    @Override
    public TaskResponse taskPriorityToUrg(TaskPriorityToUrgentRequest priority) {
        if(priority.getId().trim().isEmpty())throw new InputMismatchException("id not found");
        Optional<Task> foundTask =  taskRepository.findById(priority.getId());
        foundTask.get().setPriority(Level.URGENT);
        taskRepository.save(foundTask.get());
        return map(foundTask.get());
    }
    @Override
    public TaskResponse taskPriorityToLessUrg(TaskPriorityToLessUrgentRequest priority) {
        if(priority.getId().trim().isEmpty())throw new InputMismatchException("id not found");
        Optional<Task> foundTask =  taskRepository.findById(priority.getId());
        foundTask.get().setPriority(Level.LESS_URGENT);
        taskRepository.save(foundTask.get());
        return map(foundTask.get());
    }


    @Override
    public TaskInProgressResponse taskInProgress(TaskInProgressRequest inProgressRequest) {
        if(inProgressRequest.getId().trim().isEmpty())throw new InputMismatchException("id not found");
        Optional<Task> foundTask =  taskRepository.findById(inProgressRequest.getId());
        foundTask.get().setStatus(Status.IN_PROGRESS);
        foundTask.get().setTimeInProgress(LocalDateTime.now());
        taskRepository.save(foundTask.get());
        return mapTaskInProgress(foundTask.get());
    }

    @Override
    public TaskDoneResponse taskCompleted(TaskCompletedRequest taskCompletedRequest) {
        if(taskCompletedRequest.getId().trim().isEmpty())throw new InputMismatchException("id not found");
        Optional<Task> foundTask =  taskRepository.findById(taskCompletedRequest.getId());
        foundTask.get().setStatus(Status.COMPLETED);
        foundTask.get().setTimeDone(LocalDateTime.now());
        taskRepository.save(foundTask.get());
        return mapTaskDone(foundTask.get());
    }
    @Override
    public StartedTaskResponse startedTask(StartedTaskRequest startedTaskRequest) {
        if(startedTaskRequest.getId().trim().isEmpty())throw new InputMismatchException("Title not found");
        Optional<Task> foundTask =  taskRepository.findById(startedTaskRequest.getId());
        foundTask.get().setStatus(Status.STARTED);
        foundTask.get().setTimeStarted(LocalDateTime.now());
        taskRepository.save(foundTask.get());
        return mapStartedTask(foundTask.get());
    }

    @Override
    public Task findTaskById(String id) {
        return taskRepository.findTaskById(id);
    }

    @Override
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest) {
        Task task = new Task();
        task.setTitle(assignTaskRequest.getTitle());
        task.setAuthor(assignTaskRequest.getAuthor());
        task.setDescription(assignTaskRequest.getDescription());
        task.setPriority(assignTaskRequest.getPriority());
        task.setStatus(assignTaskRequest.getStatus());
        taskRepository.save(task);
        return mapAssignTask(task);
    }

    private static void validateTask(CreateTaskRequest createTaskRequest) {
        if(createTaskRequest.getAuthor().trim().isEmpty())throw new InputMismatchException("Invalid Input");
        if(createTaskRequest.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
    }

    private static void validateUpdate(UpdateTaskRequest task) {
        if(task.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
        if(task.getAuthor().trim().isEmpty())throw new InputMismatchException("Invalid Input");
    }

}
