package ba.edu.ibu.finance_tracker.rest.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ba.edu.ibu.finance_tracker.rest.model.CreditCard;

public interface CreditCardRepository extends MongoRepository<CreditCard, String> {

    List<CreditCard> findByUserId(String userId);

}
