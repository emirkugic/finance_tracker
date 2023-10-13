package ba.edu.ibu.finance_tracker.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.Income;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.IncomeRepository;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserService userService;

    public IncomeService(IncomeRepository incomeRepository, UserService userService) {
        this.incomeRepository = incomeRepository;
        this.userService = userService;
    }

    public Income createIncome(Income income) {
        User user = userService.getUserById(income.getUserId());
        user.setBalance(user.getBalance() + income.getAmount());
        userService.updateUserBalance(user.getId(), user.getBalance());
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

    public List<Income> getAllIncomesByParentId(String parentId) {
        List<User> children = userService.getChildrenByParentId(parentId);
        List<String> childrenIds = children.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return incomeRepository.findByUserIdIn(childrenIds);
    }

}
