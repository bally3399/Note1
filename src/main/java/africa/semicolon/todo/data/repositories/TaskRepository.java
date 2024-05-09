package africa.semicolon.todo.data.repositories;

import africa.semicolon.todo.data.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByAuthor(String username);

    Task findByTitle(String title);

    Task findTaskById(String id);
}
