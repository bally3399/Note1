package africa.semicolon.todo.data.repositories;

import africa.semicolon.todo.data.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByAuthor(String username);

    Task findByTitle(String title);
}
