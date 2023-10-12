package ba.edu.ibu.finance_tracker.core.service;

import java.util.List;
import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.Income;
import ba.edu.ibu.finance_tracker.core.repository.IncomeRepository;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Income createIncome(Income income) {
        return incomeRepository.save(income);
    }

    public void deleteIncome(String id) {
        incomeRepository.deleteById(id);
    }

    public Income updateIncomeAmount(String id, double newAmount) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Income not found"));

        income.setAmount(newAmount);
        return incomeRepository.save(income);
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public List<Income> getAllIncomesByUserId(String userId) {
        return incomeRepository.findByUserId(userId);
    }

}
