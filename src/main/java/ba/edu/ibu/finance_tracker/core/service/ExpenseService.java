package ba.edu.ibu.finance_tracker.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.Expense;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    public Expense createExpense(Expense expense) {
        User user = userService.getUserById(expense.getUserId());
        user.setBalance(user.getBalance() - expense.getAmount());
        userService.updateUserBalance(user.getId(), user.getBalance());
        return expenseRepository.save(expense);
    }

    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }

    public Expense updateExpenseAmount(String id, double newAmount) {
        Expense expenses = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));

        expenses.setAmount(newAmount);
        return expenseRepository.save(expenses);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getAllExpensesByUserId(String userId) {
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> getAllExpensesByParentId(String parentId) {
        List<User> children = userService.getChildrenByParentId(parentId);
        List<String> childrenIds = children.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return expenseRepository.findByUserIdIn(childrenIds);
    }

    public Expense transferToChild(String parentId, String childId, double amount) {
        User parent = userService.getUserById(parentId);
        User child = userService.getUserById(childId);

        if (parent.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        parent.setBalance(parent.getBalance() - amount);
        child.setBalance(child.getBalance() + amount);

        userService.updateUserBalance(parentId, parent.getBalance());
        userService.updateUserBalance(childId, child.getBalance());

        Expense transferExpense = new Expense();
        transferExpense.setUserId(parentId);
        transferExpense.setAmount(amount);
        transferExpense.setTransferToChild(true);
        transferExpense.setRecipientChildId(childId);

        return expenseRepository.save(transferExpense);
    }

}
