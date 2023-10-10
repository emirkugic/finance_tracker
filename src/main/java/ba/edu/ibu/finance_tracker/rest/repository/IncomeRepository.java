package ba.edu.ibu.finance_tracker.rest.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ba.edu.ibu.finance_tracker.rest.model.Income;

public interface IncomeRepository extends MongoRepository<Income, String> {

    List<Income> findByUserId(String userId);
}
