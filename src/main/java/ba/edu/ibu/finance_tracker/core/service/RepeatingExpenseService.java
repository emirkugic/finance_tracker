package ba.edu.ibu.finance_tracker.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.RepeatingExpense;
import ba.edu.ibu.finance_tracker.core.repository.RepeatingExpenseRepository;

@Service
public class RepeatingExpenseService {
    private RepeatingExpenseRepository repeatingExpenseRepository;

    public RepeatingExpenseService(RepeatingExpenseRepository repeatingExpenseRepository) {
        this.repeatingExpenseRepository = repeatingExpenseRepository;
    }

    public RepeatingExpense createRepeatingExpense(RepeatingExpense repeatingExpense) {
        return repeatingExpenseRepository.save(repeatingExpense);
    }

    public RepeatingExpense updateRepeatingExpense(String id, RepeatingExpense newRepeatingExpense) {
        RepeatingExpense repeatingExpense = repeatingExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repeating expense not found"));

        repeatingExpense.setAmount(newRepeatingExpense.getAmount());
        return repeatingExpenseRepository.save(repeatingExpense);
    }

    public void deleteRepeatingExpense(String id) {
        repeatingExpenseRepository.deleteById(id);
    }

    public RepeatingExpense getRepeatingExpenseById(String id) {
        return repeatingExpenseRepository.findById(id).orElse(null);
    }

    public List<RepeatingExpense> getAllRepeatingExpenses() {
        return repeatingExpenseRepository.findAll();
    }

    public List<RepeatingExpense> getAllRepeatingExpensesByUserId(String userId) {
        return repeatingExpenseRepository.findByUserId(userId);
    }
}
