package ba.edu.ibu.finance_tracker.core.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ba.edu.ibu.finance_tracker.core.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    // This is used for accessing queries from mongo repository and creating custom
    // queries
    List<User> findAllByParentId(String parentId);

}
