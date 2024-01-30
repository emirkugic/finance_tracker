package ba.edu.ibu.finance_tracker.rest.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ba.edu.ibu.finance_tracker.core.model.CreditCard;
import ba.edu.ibu.finance_tracker.core.service.CreditCardService;
import ba.edu.ibu.finance_tracker.rest.dto.CreditCardDTO.CreditCardCreateRequestDTO;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/creditcard")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> createCreditCard(@RequestBody CreditCardCreateRequestDTO creditCardRequest) {
        String response = creditCardService.createCreditCard(creditCardRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void deleteCreditCard(@PathVariable String id) {
        creditCardService.deleteCreditCard(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<CreditCard> getAllCreditCards() {
        return creditCardService.getAllCreditCards();
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public CreditCard updateCreditCardName(@PathVariable String id, @RequestBody String newName) {
        return creditCardService.updateCreditCardName(id, newName);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<CreditCard> getCreditCardsByUserId(@PathVariable String userId) {
        return creditCardService.getCreditCardsByUserId(userId);
    }

    @GetMapping("/balance/{userId}/{cardId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public double getCreditCardBalance(@PathVariable String userId, @PathVariable String cardId) {
        return creditCardService.getBalanceByCreditCardIdAndUserId(userId, cardId);
    }

    @GetMapping("/{userId}/total-cards-balances")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public double getCreditCardBalance(@PathVariable String userId) {
        return creditCardService.getTotalCreditCardBalance(userId);
    }

    @GetMapping("getCardName/{cardId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String getCardName(@PathVariable String cardId) {
        return creditCardService.getCardNameByCardId(cardId);
    }

}
