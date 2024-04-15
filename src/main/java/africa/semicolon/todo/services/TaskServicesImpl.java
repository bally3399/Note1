package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Status;
import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.repositories.TaskRepository;
import africa.semicolon.todo.dtos.request.*;
import africa.semicolon.todo.dtos.response.CreateTaskResponse;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static africa.semicolon.todo.utils.Mapper.map;
import static africa.semicolon.todo.utils.Mapper.mapTask;

@Service
public class TaskServicesImpl implements TaskServices{
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        ValidateNote(createTaskRequest);
        for(Task tasks : taskRepository.findAll()){
            if(tasks.getId().equals(createTaskRequest.getId())) throw new TaskNotFoundException("Task Already Exist");
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
        if (task.getTitle()!= null && task.getDescription() != null && task.getAuthor() != null){
            updateTask.setTitle(task.getNewTitle());
            updateTask.setAuthor(task.getAuthor());
            updateTask.setDescription(task.getNewDescription());
            updateTask.setStatus(task.getNewStatus());
        }
        Task savedTask = taskRepository.save(updateTask);
        CreateTaskResponse taskResponse = new CreateTaskResponse();
        taskResponse.setTitle(savedTask.getTitle());
        taskResponse.setAuthor(savedTask.getAuthor());
        taskResponse.setDescription(savedTask.getDescription());
        taskResponse.setStatus(savedTask.getStatus());
        return taskResponse;
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }
    @Override
    public List<Task> getAllTaskStarted(){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.STARTED == task.getStatus()) tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List<Task> getAllTaskCreated(){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.CREATED == task.getStatus()) tasks.add(task);
        }
        return tasks;
    }
    @Override
    public List<Task> getAllTaskInProgress(){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(Status.IN_PROGRESS == task.getStatus()) tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List<Task> getAllTaskCompleted(){
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
    public String deleteTask(CreateTaskRequest deleteTaskRequest) {
        if(deleteTaskRequest.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
        Task foundTask = taskRepository.findByTitle(deleteTaskRequest.getTitle());
        if (foundTask != null){
            taskRepository.delete(foundTask);
            return "Task Successfully Deleted";
        }
        throw new TaskNotFoundException("Note not found");
    }

    @Override
    public TaskResponse taskInProgress(TaskInProgressRequest inProgressRequest) {
        if(inProgressRequest.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
        Task foundTask = taskRepository.findByTitle(inProgressRequest.getTitle());
        foundTask.setStatus(Status.IN_PROGRESS);
        return map(foundTask);
    }

    @Override
    public TaskResponse taskCompleted(TaskCompletedRequest taskCompletedRequest) {
        if(taskCompletedRequest.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
        Task foundTask = taskRepository.findByTitle(taskCompletedRequest.getTitle());

        foundTask.setStatus(Status.COMPLETED);
        return map(foundTask);
    }

    @Override
    public CreateTaskResponse startedTask(StartedTaskRequest startedTaskRequest) {
        if(startedTaskRequest.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
        Task foundTask = taskRepository.findByTitle(startedTaskRequest.getTitle());
        foundTask.setStatus(Status.STARTED);
        return mapTask(foundTask);
    }

    private static void ValidateNote(CreateTaskRequest createNoteRequest) {
        if(createNoteRequest.getAuthor().trim().isEmpty())throw new InputMismatchException("Invalid Input");
        if(createNoteRequest.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
    }

    private static void validateUpdate(UpdateTaskRequest task) {
        if(task.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
        if(task.getAuthor().trim().isEmpty())throw new InputMismatchException("Invalid Input");
    }
}
