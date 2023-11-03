package ba.edu.ibu.finance_tracker.core.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

        double totalCreditCardBalance = creditCardService.getTotalCreditCardBalance(user.getId()); // Get the total
                                                                                                   // balance of all
                                                                                                   // credit cards
                                                                                                   // for this user.

        double actualCashAvailable = user.getBalance() - totalCreditCardBalance; // This is the actual cash available to
                                                                                 // the user. uses this formula:
                                                                                 // cash = user.getBalance -
                                                                                 // totalCreditCardBalance

        // Deduct expense from cash or credit card
        if ("cash".equalsIgnoreCase(source)) {
            if (actualCashAvailable >= expenseAmount) {
                // Deduct from cash if sufficient funds are available
                user.setBalance(user.getBalance() - expenseAmount);
            } else {
                // Deduct from cash and possibly from credit cards
                double remainingExpense = expenseAmount - actualCashAvailable;
                user.setBalance(user.getBalance() - actualCashAvailable);
                double totalDeductedFromCards = deductFromCreditCards(user.getId(), remainingExpense);
                // Deduct any remaining expense from the user's balance, potentially going
                // negative
                user.setBalance(user.getBalance() - totalDeductedFromCards);
            }
        } else {
            // Charge to a specific credit card
            CreditCard card = creditCardRepository.findById(source)
                    .orElseThrow(() -> new RuntimeException("CreditCard not found"));

            double remainingExpense = expenseAmount;
            if (card.getBalance() >= expenseAmount) {
                // Deduct from the card balance if it can cover the expense
                card.setBalance(card.getBalance() - expenseAmount);
            } else {
                // Deduct whatever is available from the card and then from other cards
                remainingExpense -= card.getBalance();
                card.setBalance(0);
                double totalDeductedFromOtherCards = deductFromCreditCards(user.getId(), remainingExpense);
                remainingExpense -= totalDeductedFromOtherCards;
            }
            creditCardService.updateCardBalance(card.getId(), card.getBalance());
            // Reduce the user's balance by the expense amount, potentially going negative
            user.setBalance(user.getBalance() - expenseAmount);
        }

        // Update the user balance and save the expense
        userService.updateUserBalance(user.getId(), user.getBalance());
        expenseRepository.save(expense);

        return "Creating expense was successful";
    }

    private double deductFromCreditCards(String userId, double remainingExpense) {
        List<CreditCard> cards = creditCardRepository.findAllByUserId(userId);
        double totalDeducted = 0; // Track the total amount deducted from credit cards

        for (CreditCard card : cards) {
            if (remainingExpense <= 0)
                break; // Exit early if there's no expense left

            double availableBalance = card.getBalance();
            double deduction = Math.min(remainingExpense, availableBalance);
            card.setBalance(availableBalance - deduction);
            totalDeducted += deduction;
            remainingExpense -= deduction;

            creditCardService.updateCardBalance(card.getId(), card.getBalance());
        }

        return totalDeducted;
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

}