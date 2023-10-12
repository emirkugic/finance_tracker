package ba.edu.ibu.finance_tracker.rest.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import ba.edu.ibu.finance_tracker.core.model.CreditCard;
import ba.edu.ibu.finance_tracker.core.service.CreditCardService;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping
    public CreditCard createCreditCard(@RequestBody CreditCard creditCard) {
        return creditCardService.createCreditCard(creditCard);
    }

    @DeleteMapping("/{id}")
    public void deleteCreditCard(@PathVariable String id) {
        creditCardService.deleteCreditCard(id);
    }

    @GetMapping
    public List<CreditCard> getAllCreditCards() {
        return creditCardService.getAllCreditCards();
    }

    @PutMapping
    public CreditCard updateCreditCardName(@PathVariable String id, @RequestBody String newName) {
        return creditCardService.updateCreditCardName(id, newName);
    }

    @GetMapping("/user/{userId}")
    public List<CreditCard> getCreditCardsByUserId(@PathVariable String userId) {
        return creditCardService.getCreditCardsByUserId(userId);
    }

}
