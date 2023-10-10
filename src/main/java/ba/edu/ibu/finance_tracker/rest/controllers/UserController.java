package ba.edu.ibu.finance_tracker.rest.controllers;

import ba.edu.ibu.finance_tracker.rest.model.User;
import ba.edu.ibu.finance_tracker.rest.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/email")
    public User updateEmail(@PathVariable String id, @RequestBody String newEmail) {
        return userService.updateUserEmail(id, newEmail);
    }

    @PutMapping("/{id}/password")
    public User updatePassword(@PathVariable String id, @RequestBody String newPassword) {
        return userService.updateUserPassword(id, newPassword);
    }
}
