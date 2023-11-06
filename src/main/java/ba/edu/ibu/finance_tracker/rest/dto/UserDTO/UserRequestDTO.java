package ba.edu.ibu.finance_tracker.rest.dto.UserDTO;

import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.model.enums.UserType;

public class UserRequestDTO {
    private UserType userType;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserRequestDTO() {
    }

    public UserRequestDTO(User user) {
        this.userType = user.getUserType();
        this.firstName = user.getName();
        this.lastName = user.getSurname();
        this.email = user.getUsername();
        this.password = user.getPassword();
    }

    public User toEntity() {
        User user = new User();
        user.setUserType(userType);
        user.setName(firstName);
        user.setSurname(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // public String getUsername() {
    // return email;
    // }

    // public void setUsername(String username) {
    // this.email = username;
    // }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}