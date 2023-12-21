package ba.edu.ibu.finance_tracker.core.service;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.model.enums.UserType;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.EmailUpdateResponseDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserSearchResultDTO;

@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void shouldReturnUserWhenCreated() {
        User user = new User();
        user.setId("test_id");
        user.setName("test_name");
        user.setSurname("test_surname");
        user.setEmail("test_email");
        user.setBalance(0.0);
        user.setPassword("test_password");
        user.setUserType(UserType.USER);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.createUser(user);

        Assertions.assertThat(user.getName()).isEqualTo(savedUser.getName());
        Assertions.assertThat(user.getBalance()).isNotNull();

    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = new User();
        user1.setId("1");
        user1.setName("John");
        user1.setBalance(100.0);
        user1.setUserType(UserType.USER);

        User user2 = new User();
        user2.setId("2");
        user2.setName("Jane");
        user2.setBalance(50.0);
        user2.setUserType(UserType.USER);

        List<User> users = Arrays.asList(user1, user2);

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("John");
        Assertions.assertThat(result.get(1).getName()).isEqualTo("Jane");
    }

    @Test
    void shouldReturnUserById() {
        User user = new User();
        user.setId("1");
        user.setName("John");
        user.setBalance(100.0);
        user.setUserType(UserType.USER);

        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userService.getUserById("1");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo("John");
    }

    @Test
    void shouldDeleteUser() {
        User user = new User();
        user.setId("1");
        user.setName("John");
        user.setBalance(100.0);
        user.setUserType(UserType.USER);

        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.deleteUser("1");

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    void shouldReturnChildrenByParentId() {
        User parent = new User();
        parent.setId("parent_id");
        parent.setName("Parent");
        parent.setBalance(200.0);
        parent.setUserType(UserType.USER);

        User child1 = new User();
        child1.setId("child1_id");
        child1.setName("Child1");
        child1.setBalance(50.0);
        child1.setUserType(UserType.USER);
        child1.setParentId("parent_id");

        User child2 = new User();
        child2.setId("child2_id");
        child2.setName("Child2");
        child2.setBalance(75.0);
        child2.setUserType(UserType.USER);
        child2.setParentId("parent_id");

        List<User> children = Arrays.asList(child1, child2);

        Mockito.when(userRepository.findById("parent_id")).thenReturn(Optional.of(parent));
        Mockito.when(userRepository.findAllByParentId("parent_id")).thenReturn(children);

        List<User> result = userService.getAllChildrenByParentId("parent_id");

        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("Child1");
        Assertions.assertThat(result.get(1).getName()).isEqualTo("Child2");
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersFound() {
        Mockito.when(userRepository.findByNameAndSurnameLike("NonExistent", "User"))
                .thenReturn(Arrays.asList());

        List<UserSearchResultDTO> results = userService.searchUsers("NonExistent", "User");

        assertThat(results).isEmpty();
    }

    @Test
    void shouldReturnUserBalanceById() {
        User user = new User();
        user.setId("1");
        user.setName("John");
        user.setBalance(100.0);
        user.setUserType(UserType.USER);

        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));

        double balance = userService.getBalanceByUserId("1");

        assertThat(balance).isEqualTo(100.0);
    }

    @Test
    void shouldUpdateUserBalance() {
        User user = new User();
        user.setId("1");
        user.setName("John");
        user.setBalance(100.0);
        user.setUserType(UserType.USER);

        double newBalance = 150.0;

        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = userService.updateUserBalance("1", newBalance);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isEqualTo("1");
        assertThat(userDTO.getBalance()).isEqualTo(newBalance);
    }

}