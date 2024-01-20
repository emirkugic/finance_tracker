package ba.edu.ibu.finance_tracker.rest.controllers;

import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.service.UserService;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.EmailUpdateResponseDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.NameUpdateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.PasswordUpdateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserCreateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserSearchResultDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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
@SecurityRequirement(name = "JWT Security")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserCreateRequestDTO userRequest) {
        return userService.createUser(userRequest);

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
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/updateName")
    public ResponseEntity<?> updateUserName(@PathVariable String id,
            @RequestBody NameUpdateRequestDTO nameUpdateRequest) {
        try {
            UserDTO updatedUser = userService.updateUserName(id, nameUpdateRequest);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/email")
    public EmailUpdateResponseDTO updateEmail(@PathVariable String id, @RequestBody String newEmail) {
        return userService.updateUserEmail(id, newEmail);
    }

    @PutMapping("/password/update")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequestDTO passwordUpdateRequest) {
        try {
            userService.updateUserPassword(passwordUpdateRequest);
            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/balance")
    public double getUserBalance(@PathVariable String id) {
        User user = userService.getUserById(id);
        return userService.getBalanceByUserId(user.getId());
    }

    @PutMapping("/{id}/balance")
    public UserDTO updateUserBalance(@PathVariable String id, @RequestBody double newBalance) {
        return userService.updateUserBalance(id, newBalance);
    }

    @GetMapping("/{parentId}/children")
    public List<UserDTO> getAllChildren(@PathVariable String parentId) {
        return userService.getChildrenByParentId(parentId);
    }

    @GetMapping("/isChild")
    public ResponseEntity<Boolean> isChildOfParent(@RequestParam String childId, @RequestParam String parentId) {
        return ResponseEntity.ok(userService.isChildOfParent(childId, parentId));
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

    @GetMapping("/getNameAndSurnameById")
    public String getNameAndSurname(@RequestParam String id) {
        return userService.getUserNameAndSurnameById(id);
    }

}
