package ba.edu.ibu.finance_tracker.core.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ba.edu.ibu.finance_tracker.core.model.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnAllUsers() {
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(11, users.size());

    }

    @Test
    public void shouldReturnUsersByNameAndSurnameLike() {
        List<User> users = userRepository.findByNameAndSurnameLike("Lejla", "Muratovic");
        Assertions.assertEquals(2, users.size());
    }

    @Test
    public void shouldReturnUserById() {
        Optional<User> user = userRepository.findById("654ca84e0825ec5366da9f5b");
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals("Emir", user.get().getName());
    }

}
