package africa.semicolon.todo.data.repositories;

import africa.semicolon.todo.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
