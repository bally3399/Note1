package africa.semicolon.todo.services;

import africa.semicolon.todo.data.model.Task;
import africa.semicolon.todo.data.model.Todo;
import africa.semicolon.todo.data.repositories.TaskRepository;
import africa.semicolon.todo.data.repositories.TodoRepository;
import africa.semicolon.todo.dtos.request.CreateTaskRequest;
import africa.semicolon.todo.dtos.request.UpdateTaskRequest;
import africa.semicolon.todo.dtos.response.TaskResponse;
import africa.semicolon.todo.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;

import static africa.semicolon.todo.utils.Mapper.map;

@Service
public class TaskServicesImpl implements TaskServices{
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
        ValidateNote(createTaskRequest);
        Todo todo = todoRepository.findByUsername(createTaskRequest.getAuthor());
        for(Task tasks : taskRepository.findAll()){
            if(tasks.getTitle().equals(createTaskRequest.getTitle())) throw new TaskNotFoundException("Task Title Already Exist");
        }
        if(todo != null) todo.setLoggedIn(true);
        Task task = map(createTaskRequest);
        TaskResponse response = map(task);
        taskRepository.save(task);
        return response;
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

    private static void ValidateNote(CreateTaskRequest createNoteRequest) {
        if(createNoteRequest.getAuthor().trim().isEmpty())throw new InputMismatchException("Invalid Input");
        if(createNoteRequest.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
    }

    @Override
    public TaskResponse updateTask(UpdateTaskRequest task) {
        validateUpdate(task);
        Task updateTask = taskRepository.findByTitle(task.getTitle());
        if (task.getTitle()!= null && task.getDescription() != null && task.getAuthor() != null){
            updateTask.setTitle(task.getNewTitle());
            updateTask.setAuthor(task.getAuthor());
            updateTask.setDescription(task.getNewDescription());
        }
        Task savedTask = taskRepository.save(updateTask);
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTitle(savedTask.getTitle());
        taskResponse.setAuthor(savedTask.getAuthor());
        taskResponse.setDescription(savedTask.getDescription());
        return taskResponse;
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
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

    private static void validateUpdate(UpdateTaskRequest task) {
        if(task.getTitle().trim().isEmpty())throw new InputMismatchException("Title not found");
        if(task.getAuthor().trim().isEmpty())throw new InputMismatchException("Invalid Input");
    }
}