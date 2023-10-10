package ba.edu.ibu.finance_tracker.rest.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ba.edu.ibu.finance_tracker.rest.model.Expense;
import ba.edu.ibu.finance_tracker.rest.repository.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository expensesRepository;

    public ExpenseService(ExpenseRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }

    public Expense createExpense(Expense expenses) {
        return expensesRepository.save(expenses);
    }

    public void deleteExpense(String id) {
        expensesRepository.deleteById(id);
    }

    public Expense updateExpenseAmount(String id, double newAmount) {
        Expense expenses = expensesRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));

        expenses.setAmount(newAmount);
        return expensesRepository.save(expenses);
    }

    public List<Expense> getAllExpenses() {
        return expensesRepository.findAll();
    }

    public List<Expense> getAllExpensesByUserId(String userId) {
        return expensesRepository.findByUserId(userId);
    }
}
