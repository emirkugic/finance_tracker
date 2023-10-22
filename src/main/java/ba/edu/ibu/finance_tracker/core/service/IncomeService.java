package ba.edu.ibu.finance_tracker.core.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.Income;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.IncomeRepository;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserDTO;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public IncomeService(IncomeRepository incomeRepository, UserService userService, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Income createIncome(Income income) {
        Optional<User> existingUser = userRepository.findById(income.getUserId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }

        User user = userService.getUserById(income.getUserId());
        user.setBalance(user.getBalance() + income.getAmount());
        userService.updateUserBalance(user.getId(), user.getBalance());
        if (income.getReceivedDate() == null) {
            income.setReceivedDate(LocalDateTime.now()); // need help with this one why it doesnt work :(
        }
        return incomeRepository.save(income);
    }

    public void deleteIncome(String id) {
        Optional<Income> existingIncome = incomeRepository.findById(id);
        if (existingIncome.isEmpty()) {
            throw new RuntimeException("Income ID not found");
        }
        incomeRepository.deleteById(id);
    }

    public String updateIncomeAmount(String id, double newAmount) {
        Optional<Income> existingIncome = incomeRepository.findById(id);
        if (existingIncome.isEmpty()) {
            throw new RuntimeException("Income ID not found");
        }

        Income income = existingIncome.get();
        income.setAmount(newAmount);
        incomeRepository.save(income);

        return "Update successful";
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public List<Income> getAllIncomesByUserId(String userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }
        return incomeRepository.findByUserId(userId);
    }

    public List<Income> getAllIncomesByParentId(String parentId) {
        Optional<User> existingUser = userRepository.findById(parentId);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }

        List<UserDTO> children = userService.getChildrenByParentId(parentId);
        List<String> childrenIds = children.stream()
                .map(UserDTO::getId)
                .collect(Collectors.toList());
        return incomeRepository.findByUserIdIn(childrenIds);
    }

}
