package ba.edu.ibu.finance_tracker.core.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ba.edu.ibu.finance_tracker.core.model.Expense;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.ExpenseRepository;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserService userService, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public String createExpense(Expense expense) {
        Optional<User> existingUser = userRepository.findById(expense.getUserId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }

        if (expense.getRecipientChildId() != null) {
            Optional<User> existingChild = userRepository.findById(expense.getRecipientChildId());
            if (existingChild.isEmpty()) {
                throw new RuntimeException("ChildID doesn't exist");
            }
            expense.setTransferToChild(true);
        } else {
            expense.setTransferToChild(false);
        }

        User user = existingUser.get();
        user.setBalance(user.getBalance() - expense.getAmount());
        userService.updateUserBalance(user.getId(), user.getBalance());
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(Date.from(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        }
        expenseRepository.save(expense);

        return "Creating expense was successful";
    }

    public void deleteExpense(String id) {
        Optional<Expense> existingExpense = expenseRepository.findById(id);
        if (existingExpense.isEmpty()) {
            throw new RuntimeException("ExpenseID doesn't exist");
        }
        expenseRepository.deleteById(id);
    }

    public boolean updateExpenseAmount(String id, double newAmount) {
        Optional<Expense> existingExpense = expenseRepository.findById(id);
        if (existingExpense.isEmpty()) {
            throw new RuntimeException("ExpenseID doesn't exist");
        }
        Expense expense = existingExpense.get();
        expense.setAmount(newAmount);
        expenseRepository.save(expense);
        return true;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getAllExpensesByUserId(String userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }
        return expenseRepository.findByUserId(userId);
    }

    // ignore for now
    // public List<Expense> getAllExpensesByParentId(String parentId) {
    // Optional<User> existingUser = userRepository.findById(parentId);
    // if (existingUser.isEmpty()) {
    // throw new RuntimeException("UserID doesn't exist");
    // }

    // List<User> children = userService.getChildrenByParentId(parentId);
    // List<String> childrenIds = children.stream()
    // .map(User::getId)
    // .collect(Collectors.toList());
    // return expenseRepository.findByUserIdIn(childrenIds);
    // }

    public Expense transferToChild(String parentId, String childId, double amount) {
        Optional<User> existingUser = userRepository.findById(parentId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("ParentID doesn't exist");
        }
        Optional<User> existingChild = userRepository.findById(childId);
        if (existingChild.isEmpty()) {
            throw new RuntimeException("ChildID doesn't exist");
        }

        User parent = userService.getUserById(parentId);
        User child = userService.getUserById(childId);

        // if (parent.getBalance() < amount) {
        // throw new RuntimeException("Insufficient balance");
        // }

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

    public List<Expense> getAllExpensesBetweenDates(String userId, LocalDate startDate, LocalDate endDate) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }

        LocalDateTime startOfStartDate = startDate.atStartOfDay();
        LocalDateTime endOfEndDate = endDate.atTime(23, 59, 59, 999999999);

        return expenseRepository.findByUserIdAndExpenseDateBetween(userId, startOfStartDate, endOfEndDate);
    }

    public List<Expense> getAllExpensesByDate(String userId, LocalDate expenseDate) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }

        LocalDateTime startOfDay = expenseDate.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        return expenseRepository.findByUserIdAndExpenseDateBetween(userId,
                startOfDay, endOfDay);
    }

}