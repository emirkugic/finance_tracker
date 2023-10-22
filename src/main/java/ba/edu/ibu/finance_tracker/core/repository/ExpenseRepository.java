package ba.edu.ibu.finance_tracker.core.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import ba.edu.ibu.finance_tracker.core.model.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findByUserId(String userId);

    List<Expense> findByUserIdIn(List<String> userIds);

}
