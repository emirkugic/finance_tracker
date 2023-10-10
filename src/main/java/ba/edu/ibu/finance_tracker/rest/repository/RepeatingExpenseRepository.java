package ba.edu.ibu.finance_tracker.rest.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ba.edu.ibu.finance_tracker.rest.model.RepeatingExpense;

public interface RepeatingExpenseRepository extends MongoRepository<RepeatingExpense, String> {
    List<RepeatingExpense> findByUserId(String userId);
}
