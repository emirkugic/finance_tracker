package ba.edu.ibu.finance_tracker.rest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.edu.ibu.finance_tracker.rest.model.RepeatingExpense;
import ba.edu.ibu.finance_tracker.rest.service.RepeatingExpenseService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/repeating-expenses")
public class RepeatingExpenseController {

    private RepeatingExpenseService repeatingExpenseService;

    public RepeatingExpenseController(RepeatingExpenseService repeatingExpenseService) {
        this.repeatingExpenseService = repeatingExpenseService;
    }

    @PostMapping
    public RepeatingExpense createRepeatingExpense(@RequestBody RepeatingExpense repeatingExpense) {
        return repeatingExpenseService.createRepeatingExpense(repeatingExpense);
    }

    @DeleteMapping("/{id}")
    public void deleteRepeatingExpense(@PathVariable String id) {
        repeatingExpenseService.deleteRepeatingExpense(id);
    }

    @GetMapping
    public List<RepeatingExpense> getAllRepeatingExpenses() {
        return repeatingExpenseService.getAllRepeatingExpenses();
    }

    @PutMapping
    public RepeatingExpense updateRepeatingExpense(@PathVariable String id,
            @RequestBody RepeatingExpense repeatingExpense) {
        return repeatingExpenseService.updateRepeatingExpense(id, repeatingExpense);
    }

    @GetMapping("/user/{userId}")
    public List<RepeatingExpense> getAllRepeatingExpensesByUserId(@PathVariable String userId) {
        return repeatingExpenseService.getAllRepeatingExpensesByUserId(userId);
    }

}
