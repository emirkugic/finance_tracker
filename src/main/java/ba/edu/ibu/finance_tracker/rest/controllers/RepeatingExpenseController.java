package ba.edu.ibu.finance_tracker.rest.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import ba.edu.ibu.finance_tracker.core.model.RepeatingExpense;
import ba.edu.ibu.finance_tracker.core.service.RepeatingExpenseService;
import ba.edu.ibu.finance_tracker.rest.dto.RepeatingExpensesDTO.RepeatingExpenseCreateRequest;

@RestController
@RequestMapping("/api/repeating-expenses")
public class RepeatingExpenseController {

    private final RepeatingExpenseService repeatingExpenseService;

    public RepeatingExpenseController(RepeatingExpenseService repeatingExpenseService) {
        this.repeatingExpenseService = repeatingExpenseService;
    }

    @PostMapping
    public RepeatingExpense createRepeatingExpense(@RequestBody RepeatingExpenseCreateRequest requestDto) {
        RepeatingExpense repeatingExpense = new RepeatingExpense();
        repeatingExpense.setUserId(requestDto.getUserId());
        repeatingExpense.setAmount(requestDto.getAmount());
        repeatingExpense.setCategory(requestDto.getCategory());
        repeatingExpense.setDueDate(requestDto.getDueDate());

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

    @PutMapping("/{id}")
    public RepeatingExpense updateRepeatingExpense(@PathVariable String id,
            @RequestBody RepeatingExpense repeatingExpense) {
        return repeatingExpenseService.updateRepeatingExpense(id, repeatingExpense);
    }

    @GetMapping("/user/{userId}")
    public List<RepeatingExpense> getAllRepeatingExpensesByUserId(@PathVariable String userId) {
        return repeatingExpenseService.getAllRepeatingExpensesByUserId(userId);
    }
}
