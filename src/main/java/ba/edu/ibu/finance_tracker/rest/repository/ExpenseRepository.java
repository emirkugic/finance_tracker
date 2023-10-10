package ba.edu.ibu.finance_tracker.rest.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import ba.edu.ibu.finance_tracker.rest.model.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByUserId(String userId);
}
