package ba.edu.ibu.finance_tracker.core.repository;

import ba.edu.ibu.finance_tracker.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByParentId(String parentId);

    @Query("{ 'name': { $regex: ?0, $options: 'i' }, 'surname': { $regex: ?1, $options: 'i' } }")
    List<User> findByNameAndSurnameLike(String name, String surname);

}
