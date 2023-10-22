package ba.edu.ibu.finance_tracker.core.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.RepeatingExpense;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.RepeatingExpenseRepository;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;

@Service
public class RepeatingExpenseService {
    private RepeatingExpenseRepository repeatingExpenseRepository;
    private UserRepository userRepository;

    public RepeatingExpenseService(RepeatingExpenseRepository repeatingExpenseRepository,
            UserRepository userRepository) {
        this.repeatingExpenseRepository = repeatingExpenseRepository;
    }

    public RepeatingExpense createRepeatingExpense(RepeatingExpense repeatingExpense) {
        if (repeatingExpense.getDueDate() == null) {
            repeatingExpense.setDueDate(LocalDateTime.now());
        }
        return repeatingExpenseRepository.save(repeatingExpense);
    }

    public RepeatingExpense updateRepeatingExpense(String id, RepeatingExpense newRepeatingExpense) {
        Optional<RepeatingExpense> repeatingExpenseOptional = repeatingExpenseRepository.findById(id);
        if (!repeatingExpenseOptional.isPresent()) {
            throw new RuntimeException("Repeating expense not found");
        }

        RepeatingExpense repeatingExpense = repeatingExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repeating expense not found"));

        repeatingExpense.setAmount(newRepeatingExpense.getAmount());
        return repeatingExpenseRepository.save(repeatingExpense);
    }

    public void deleteRepeatingExpense(String id) {
        Optional<RepeatingExpense> repeatingExpenseOptional = repeatingExpenseRepository.findById(id);
        if (repeatingExpenseOptional.isEmpty()) {
            throw new RuntimeException("Repeating expense not found");
        }
        repeatingExpenseRepository.deleteById(id);
    }

    public RepeatingExpense getRepeatingExpenseById(String id) {
        Optional<RepeatingExpense> repeatingExpenseOptional = repeatingExpenseRepository.findById(id);
        if (repeatingExpenseOptional.isEmpty()) {
            throw new RuntimeException("Repeating expense not found");
        }

        return repeatingExpenseRepository.findById(id).orElse(null);
    }

    public List<RepeatingExpense> getAllRepeatingExpenses() {
        return repeatingExpenseRepository.findAll();
    }

    public List<RepeatingExpense> getAllRepeatingExpensesByUserId(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return repeatingExpenseRepository.findByUserId(userId);
    }
}
