package ba.edu.ibu.finance_tracker.core.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ba.edu.ibu.finance_tracker.core.model.RepeatingExpense;

public interface RepeatingExpenseRepository extends MongoRepository<RepeatingExpense, String> {
    List<RepeatingExpense> findByUserId(String userId);
}
