package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Level;
import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.repositories.TaskRepository;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.exceptions.TaskNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        for(Task tasks : taskRepository.findAll()){
            if(tasks.getTitle().equals(createTaskRequest.getTitle())&& createTaskRequest.getAuthor().equals(tasks.getAuthor())) throw new TaskNotFoundException("Task Already Exist");
        }
        Task task = map(createTaskRequest);
        task.setStatus(Status.CREATED);
        CreateTaskResponse response = mapTask(task);
        taskRepository.save(task);
        return response;
    }

    @Override
    public CreateTaskResponse updateTask(UpdateTaskRequest task) {
        validateUpdate(task);
        Task updateTask = taskRepository.findByTitle(task.getTitle());
        if (task.getTitle()!= null && task.getPriority() != null && task.getAuthor() != null){
            updateTask.setTitle(task.getNewTitle());
            updateTask.setAuthor(task.getAuthor());
            updateTask.setPriority(task.getNewPriority());
            updateTask.setDescription(task.getDescription());
        }
        Task savedTask = taskRepository.save(updateTask);
        CreateTaskResponse taskResponse = new CreateTaskResponse();
        taskResponse.setTitle(savedTask.getTitle());
        taskResponse.setAuthor(savedTask.getAuthor());
        taskResponse.setPriority(savedTask.getPriority());
        taskResponse.setDescription(savedTask.getDescription());
        taskResponse.setStatus(savedTask.getStatus());
        return taskResponse;
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }
    @Override
    public List<Task> getAllTaskStarted(String user){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findByAuthor(user)){
            if(Status.STARTED == task.getStatus()) tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List<Task> getAllTaskCreated(String user){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.CREATED == task.getStatus()) tasks.add(task);
        }
        return tasks;
    }
    @Override
    public List<Task> getAllTaskInProgress(String user){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.IN_PROGRESS == task.getStatus()) tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List<Task> getAllTaskCompleted(String user){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.COMPLETED == task.getStatus()) tasks.add(task);
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
    public TaskResponse taskInProgress(TaskInProgressRequest inProgressRequest) {
        if(inProgressRequest.getId().trim().isEmpty())throw new InputMismatchException("id not found");
        Optional<Task> foundTask =  taskRepository.findById(inProgressRequest.getId());
        foundTask.get().setStatus(Status.IN_PROGRESS);
        taskRepository.save(foundTask.get());
        return map(foundTask.get());
    }

    @Override
    public TaskResponse taskCompleted(TaskCompletedRequest taskCompletedRequest) {
        if(taskCompletedRequest.getId().trim().isEmpty())throw new InputMismatchException("id not found");
        Optional<Task> foundTask =  taskRepository.findById(taskCompletedRequest.getId());
        foundTask.get().setStatus(Status.COMPLETED);
        taskRepository.save(foundTask.get());
        return map(foundTask.get());
    }
    @Override
    public CreateTaskResponse startedTask(StartedTaskRequest startedTaskRequest) {
        if(startedTaskRequest.getId().trim().isEmpty())throw new InputMismatchException("Title not found");
        Optional<Task> foundTask =  taskRepository.findById(startedTaskRequest.getId());
        foundTask.get().setStatus(Status.STARTED);
        taskRepository.save(foundTask.get());
        return mapTask(foundTask.get());
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
