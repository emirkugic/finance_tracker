package ba.edu.ibu.finance_tracker.core.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.CreditCard;
import ba.edu.ibu.finance_tracker.core.model.Expense;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.CreditCardRepository;
import ba.edu.ibu.finance_tracker.core.repository.ExpenseRepository;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;
import ba.edu.ibu.finance_tracker.rest.dto.ExpenseDTO.ExpenseCreateRequestDTO;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CreditCardService creditCardService;
    private final CreditCardRepository creditCardRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserService userService, UserRepository userRepository,
            CreditCardService creditCardService, CreditCardRepository creditCardRepository) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.creditCardService = creditCardService;
        this.creditCardRepository = creditCardRepository;
    }

    public String createExpense(ExpenseCreateRequestDTO expenseDTO) {
        Optional<User> existingUser = userRepository.findById(expenseDTO.getUserId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }

        User user = existingUser.get();
        Date expenseDate = expenseDTO.getExpenseDate() != null ? expenseDTO.getExpenseDate()
                : Date.from(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());

        Expense expense = new Expense();
        expense.setUserId(expenseDTO.getUserId());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setSource(expenseDTO.getSource());
        expense.setExpenseDate(expenseDate);
        expense.setRecipientChildId(expenseDTO.getRecipientChildId());
        expense.setTransferToChild(expenseDTO.getRecipientChildId() != null);

        String source = expense.getSource(); // this is the credit card id
        double expenseAmount = expense.getAmount();

        // Deduct expense from cash or credit card
        if (source.equalsIgnoreCase("cash")) {
            handleCashExpense(user, expenseAmount);
        } else {
            handleCardExpense(user, source, expenseAmount);
        }

        // Update the user balance and save the expense
        userService.updateUserBalance(user.getId(), user.getBalance());
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

    public List<Expense> getAllExpensesByCategory(String userId, String category) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }
        return expenseRepository.findByUserIdAndCategoryIgnoreCase(userId, category);
    }

    public List<Expense> getExpensesBySource(String userId, String source) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }
        return expenseRepository.findByUserIdAndSourceIgnoreCase(userId, source);
    }

    public double getSumAmountByCategoryOrSourceAndDateRange(
            String userId,
            Optional<String> category,
            Optional<String> source,
            Optional<LocalDate> startDate,
            Optional<LocalDate> endDate) {

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }

        // If startDate or endDate is not provided, use default values aka. from 2020 to
        // 2120
        LocalDateTime startDateTime = startDate.orElse(LocalDate.of(2020, 1, 1)).atStartOfDay();
        LocalDateTime endDateTime = endDate.orElse(LocalDate.now().plusYears(100)).atTime(23, 59, 59, 999999999);

        // Convert LocalDateTime to Date
        Date start = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

        List<Expense> expenses = expenseRepository.findByUserIdAndExpenseDateBetween(userId, start, end);

        // Apply the category and/or source filters if they are present
        Stream<Expense> filteredExpenses = expenses.stream();
        if (category.isPresent()) {
            filteredExpenses = filteredExpenses
                    .filter(expense -> expense.getCategory().equalsIgnoreCase(category.get()));
        }
        if (source.isPresent()) {
            filteredExpenses = filteredExpenses.filter(expense -> expense.getSource().equalsIgnoreCase(source.get()));
        }

        return filteredExpenses.mapToDouble(Expense::getAmount).sum();
    }

    // ! =======================================! //
    // ! HELPER METHODS USED IN createExpense() ! //
    // ! =======================================! //

    private void handleCashExpense(User user, double expenseAmount) {
        double totalCreditCardBalance = creditCardService.getTotalCreditCardBalance(user.getId()); // Get the total
                                                                                                   // balance of all
                                                                                                   // credit cards
                                                                                                   // for this user.
        double actualCashAvailable = user.getBalance() - totalCreditCardBalance; // This is the actual cash available to
                                                                                 // the user. uses this formula:
                                                                                 // cash = user.getBalance -
                                                                                 // totalCreditCardBalance
        // Deduct from cash if sufficient funds are available
        if (actualCashAvailable >= expenseAmount) {
            user.setBalance(user.getBalance() - expenseAmount);
        } else {
            double remainingExpense = expenseAmount - actualCashAvailable;
            user.setBalance(user.getBalance() - actualCashAvailable); // Spend all cash available
            double totalDeductedFromCards = deductFromCreditCards(user.getId(), remainingExpense);
            user.setBalance(user.getBalance() - totalDeductedFromCards); // Deduct remaining expense from credit cards
        }
    }

    private double deductFromCreditCards(String userId, double remainingExpense) {
        List<CreditCard> cards = creditCardRepository.findAllByUserId(userId);
        double totalDeducted = 0;

        for (CreditCard card : cards) {
            if (remainingExpense <= 0)
                break;

            double availableBalance = card.getBalance();
            double deduction = Math.min(remainingExpense, availableBalance);
            card.setBalance(availableBalance - deduction);
            totalDeducted += deduction;
            remainingExpense -= deduction;

            creditCardService.updateCardBalance(card.getId(), card.getBalance());
        }

        return totalDeducted;
    }

    private void handleCardExpense(User user, String cardId, double expenseAmount) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("CreditCard not found"));
        double remainingExpense = expenseAmount;

        if (card.getBalance() >= expenseAmount) {
            card.setBalance(card.getBalance() - expenseAmount);
        } else {
            remainingExpense -= card.getBalance();
            card.setBalance(0); // Max out the card
            remainingExpense -= deductFromCreditCards(user.getId(), remainingExpense);
        }
        creditCardService.updateCardBalance(card.getId(), card.getBalance());
        user.setBalance(user.getBalance() - expenseAmount); // Reduce user balance by expense amount
    }

}