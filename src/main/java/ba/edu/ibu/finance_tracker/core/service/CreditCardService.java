package ba.edu.ibu.finance_tracker.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.CreditCard;
import ba.edu.ibu.finance_tracker.core.repository.CreditCardRepository;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public CreditCard createCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
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
        return creditCardRepository.findByUserId(userId);
    }

}
