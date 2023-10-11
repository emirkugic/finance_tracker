package ba.edu.ibu.finance_tracker.rest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import ba.edu.ibu.finance_tracker.rest.model.Income;
import ba.edu.ibu.finance_tracker.rest.service.IncomeService;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    public Income createIncome(@RequestBody Income income) {
        return incomeService.createIncome(income);
    }

    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable String id) {
        incomeService.deleteIncome(id);
    }

    @PutMapping("/{id}")
    public Income updateIncomeAmount(@PathVariable String id, @RequestBody double newAmount) {
        return incomeService.updateIncomeAmount(id, newAmount);
    }

    @GetMapping("/user/{userId}")
    public List<Income> getAllIncomesByUserId(@PathVariable String userId) {
        return incomeService.getAllIncomesByUserId(userId);
    }

    @GetMapping
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }
}
