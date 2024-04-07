package africa.semicolon.todo.data.repositories;

import africa.semicolon.todo.data.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {

    Todo findByUsername(String username);
}
