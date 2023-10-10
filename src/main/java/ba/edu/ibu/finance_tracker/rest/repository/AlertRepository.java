package ba.edu.ibu.finance_tracker.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ba.edu.ibu.finance_tracker.rest.model.Alert;

public interface AlertRepository extends MongoRepository<Alert, String> {

}
