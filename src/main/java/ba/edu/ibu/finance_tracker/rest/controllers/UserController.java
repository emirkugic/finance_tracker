package ba.edu.ibu.finance_tracker.rest.controllers;

import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.service.UserService;
import ba.edu.ibu.finance_tracker.rest.dto.UserSearchResultDTO;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}/email")
    public User updateEmail(@PathVariable String id, @RequestBody String newEmail) {
        return userService.updateUserEmail(id, newEmail);
    }

    @PutMapping("/{id}/password")
    public User updatePassword(@PathVariable String id, @RequestBody String newPassword) {
        return userService.updateUserPassword(id, newPassword);
    }

    @GetMapping("/{id}/balance")
    public double getUserBalance(@PathVariable String id) {
        User user = userService.getUserById(id);
        return user.getBalance();
    }

    @PutMapping("/{id}/balance")
    public User updateUserBalance(@PathVariable String id, @RequestBody double newBalance) {
        return userService.updateUserBalance(id, newBalance);
    }

    @GetMapping("/{parentId}/children")
    public List<User> getAllChildren(@PathVariable String parentId) {
        return userService.getAllChildren(parentId);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestParam String name,
            @RequestParam String surname) {
        List<UserSearchResultDTO> results = userService.searchUsers(name, surname);
        if (results.isEmpty()) {
            return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/send-to-all")
    public String sendEmailToAllUsers(@RequestParam String message) {
        return userService.sendEmailToAllUsers(message);
    }

}
