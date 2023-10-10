package ba.edu.ibu.finance_tracker.rest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Income createIncome(Income income) {
        return incomeService.createIncome(income);
    }

    @DeleteMapping("/{id}")
    public void deleteIncome(String id) {
        incomeService.deleteIncome(id);
    }

    @PutMapping
    public Income updateIncomeAmount(String id, double newAmount) {
        return incomeService.updateIncomeAmount(id, newAmount);
    }

    @GetMapping("user/{userId}")
    public List<Income> getAllIncomesByUserId(String userId) {
        return incomeService.getAllIncomesByUserId(userId);
    }

    @GetMapping
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }
}
