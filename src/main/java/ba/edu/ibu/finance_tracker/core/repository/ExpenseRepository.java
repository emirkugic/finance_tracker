package ba.edu.ibu.finance_tracker.core.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import ba.edu.ibu.finance_tracker.core.model.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findByUserId(String userId);

    List<Expense> findByUserIdIn(List<String> userIds);

    List<Expense> findByUserIdAndExpenseDateBetween(String userId, Date startDate, Date endDate);

    List<Expense> findByUserIdAndExpenseDateBetween(String userId, LocalDateTime start, LocalDateTime end);

}
