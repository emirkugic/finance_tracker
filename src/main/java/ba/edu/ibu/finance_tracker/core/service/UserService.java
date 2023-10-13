package ba.edu.ibu.finance_tracker.core.service;

import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserEmail(String id, String newEmail) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Email not found"));

        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    public User updateUserPassword(String id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUserBalance(String id, double newBalance) {
        User user = getUserById(id);
        user.setBalance(newBalance);
        return userRepository.save(user);
    }

    public List<User> getChildrenByParentId(String parentId) {
        return userRepository.findAllByParentId(parentId);
    }

    public boolean isChildOfParent(String childId, String parentId) {
        User child = getUserById(childId);
        return parentId.equals(child.getParentId());
    }

    public List<User> getAllChildren(String parentId) {
        return userRepository.findAllByParentId(parentId);
    }
}
