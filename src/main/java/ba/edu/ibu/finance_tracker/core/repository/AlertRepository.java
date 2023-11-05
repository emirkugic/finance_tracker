package ba.edu.ibu.finance_tracker.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ba.edu.ibu.finance_tracker.core.model.Alert;

public interface AlertRepository extends MongoRepository<Alert, String> {

}
