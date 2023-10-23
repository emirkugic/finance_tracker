package ba.edu.ibu.finance_tracker.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ba.edu.ibu.finance_tracker.core.model.Income;

public interface IncomeRepository extends MongoRepository<Income, String> {

    List<Income> findByUserId(String userId);

    List<Income> findByUserIdIn(List<String> userIds);

    List<Income> findByUserIdAndReceivedDateBetween(String userId, Date startDate, Date endDate);
}
