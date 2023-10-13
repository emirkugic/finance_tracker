package ba.edu.ibu.finance_tracker.rest.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.edu.ibu.finance_tracker.core.model.Expense;
import ba.edu.ibu.finance_tracker.core.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    final private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping()
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseService.createExpense(expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    @PutMapping("/{id}/newAmount")
    public Expense updateExpenseAmount(@PathVariable String id, @RequestBody double newAmount) {
        return expenseService.updateExpenseAmount(id, newAmount);
    }

    @GetMapping
    List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("user/{userId}")
    public List<Expense> getExpensesByUserId(@PathVariable String userId) {
        return expenseService.getAllExpensesByUserId(userId);
    }

    @GetMapping("/parent/{parentId}")
    public List<Expense> getAllExpensesByParentId(@PathVariable String parentId) {
        return expenseService.getAllExpensesByParentId(parentId);
    }

    @PostMapping("/transfer/{parentId}/{childId}")
    public Expense transferToChild(@PathVariable String parentId, @PathVariable String childId,
            @RequestBody double amount) {
        return expenseService.transferToChild(parentId, childId, amount);
    }

}
