package ba.edu.ibu.finance_tracker.core.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ba.edu.ibu.finance_tracker.core.model.CreditCard;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.CreditCardRepository;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;
import ba.edu.ibu.finance_tracker.rest.dto.CreditCardDTO.CreditCardCreateRequestDTO;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final UserRepository userRepository;

    public CreditCardService(CreditCardRepository creditCardRepository, UserRepository userRepository) {
        this.creditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
    }

    public String createCreditCard(CreditCardCreateRequestDTO creditCardRequest) {
        CreditCard creditCard = new CreditCard();
        creditCard.setUserId(creditCardRequest.getUserId());
        creditCard.setCardNumber(creditCardRequest.getCardNumber());
        creditCard.setCardName(creditCardRequest.getCardName());

        Optional<User> user = userRepository.findById(creditCard.getUserId());
        if (user.isEmpty()) {
            throw new RuntimeException("User ID does not exist.");
        }

        if (!creditCard.getCardNumber().matches("\\d{4}")) {
            throw new RuntimeException("Invalid credit card format. Only the last 4 digits are required.");
        }
        if (creditCard.getCardName().isEmpty()) {
            throw new RuntimeException("Credit card name is required.");
        }

        Optional<CreditCard> existingCreditCard = creditCardRepository.findByUserIdAndCardNumber(creditCard.getUserId(),
                creditCard.getCardNumber());

        if (existingCreditCard.isPresent()) {
            throw new RuntimeException("The same user has already added this credit card.");
        }

        creditCardRepository.save(creditCard);

        return "Credit card successfully created.";
    }

    public void deleteCreditCard(String id) {
        creditCardRepository.deleteById(id);
    }

    public CreditCard updateCreditCardName(String id, String newName) {
        CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit card not found"));

        creditCard.setCardName(newName);
        return creditCardRepository.save(creditCard);
    }

    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public List<CreditCard> getCreditCardsByUserId(String userId) {
        return creditCardRepository.findAllByUserId(userId);
    }

}
