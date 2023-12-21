package ba.edu.ibu.finance_tracker.core.model;

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ba.edu.ibu.finance_tracker.core.model.enums.UserType;

public class UserTest {

    @Test
    void shouldCreateNewUser() {
        User user = new User("1", "Emir", "Kugic", "emir@gmail.com2", "emir", 6.0, UserType.USER, null);

        Assertions.assertEquals("Emir", user.getName());
        Assertions.assertEquals("1", user.getId());

    }

    @Test
    void shouldCompareTwoUsers() {
        User user1 = new User("1", "Metro", "Boomin", "metroo@gmail.com2", "emir", 6.0, UserType.USER, null);
        User user2 = new User("1", "Metro", "Boomin", "metroo@gmail.com2", "emir", 6.0, UserType.USER, null);

        AssertionsForInterfaceTypes
                .assertThat(user1)
                .usingRecursiveComparison()
                .isEqualTo(user2);
    }

}
