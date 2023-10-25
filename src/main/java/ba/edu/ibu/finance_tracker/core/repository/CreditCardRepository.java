package ba.edu.ibu.finance_tracker.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ba.edu.ibu.finance_tracker.core.model.CreditCard;

public interface CreditCardRepository extends MongoRepository<CreditCard, String> {

    List<CreditCard> findAllByUserId(String userId);

    Optional<CreditCard> findById(String id);

    Optional<CreditCard> findByCardNumber(String cardNumber);

    Optional<CreditCard> findByUserIdAndCardNumber(String userId, String cardNumber);

}
