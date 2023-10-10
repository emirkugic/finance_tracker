package ba.edu.ibu.finance_tracker.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ba.edu.ibu.finance_tracker.rest.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    // This is used for custom queries
}
