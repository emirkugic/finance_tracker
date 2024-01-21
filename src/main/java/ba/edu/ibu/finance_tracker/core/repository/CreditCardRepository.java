package ba.edu.ibu.finance_tracker.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import ba.edu.ibu.finance_tracker.core.model.CreditCard;

public interface CreditCardRepository extends MongoRepository<CreditCard, String> {

    List<CreditCard> findAllByUserId(String userId);

    @NonNull
    Optional<CreditCard> findById(@NonNull String id);

    Optional<CreditCard> findByCardNumber(String cardNumber);

    Optional<CreditCard> findByUserIdAndCardNumber(String userId, String cardNumber);

    Optional<CreditCard> getCardNameById(String cardId);

}
