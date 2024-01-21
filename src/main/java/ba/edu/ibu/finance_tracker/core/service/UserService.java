package ba.edu.ibu.finance_tracker.core.service;

import ba.edu.ibu.finance_tracker.core.api.mailsender.MailSender;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.EmailUpdateResponseDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.NameUpdateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.PasswordUpdateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserCreateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserSearchResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private MailSender mailgunSender;
    // @Autowired
    // private MailSender sendgridSender;

    public String sendEmailToAllUsers(String message) {
        List<User> users = userRepository.findAll();

        return mailgunSender.send(users, message);

    }

    // security
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found."));
            }
        };
    }

    // CRUD and my own methods

    public User createUser(User user) { // this is used in UserServiceTest (bc of Mockito)
        return userRepository.save(user);
    }

    public UserDTO createUser(UserCreateRequestDTO userRequest) {
        Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User newUser = new User();
        newUser.setName(userRequest.getName());
        newUser.setSurname(userRequest.getSurname());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(userRequest.getPassword());
        newUser.setBalance(userRequest.getBalance());
        newUser.setParentId(userRequest.getParentId());

        return new UserDTO(userRepository.save(newUser));
    }

    public void deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO updateUserName(String id, NameUpdateRequestDTO nameUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(nameUpdateRequest.getName());
        user.setSurname(nameUpdateRequest.getSurname());
        userRepository.save(user);
        return new UserDTO(user);
    }

    public EmailUpdateResponseDTO updateUserEmail(String id, String newEmail) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("ID not found"));

        user.setEmail(newEmail);
        User savedUser = userRepository.save(user);

        return new EmailUpdateResponseDTO(savedUser.getId(), savedUser.getUsername());
    }

    // public boolean updateUserPassword(PasswordUpdateRequestDTO request) {
    // User user = userRepository.findById(request.getUserId())
    // .orElseThrow(() -> new RuntimeException("User not found"));

    // if (!user.getPassword().equals(request.getOldPassword())) {
    // throw new RuntimeException("Old password does not match");
    // }

    // if (request.getNewPassword().equals(request.getOldPassword())) {
    // throw new RuntimeException("New password cannot be the same as the old
    // password");
    // }

    // user.setPassword(request.getNewPassword());
    // userRepository.save(user);

    // return true;
    // }

    public UserDTO updateUserBalance(String id, double newBalance) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            user.get().setBalance(newBalance);
            return new UserDTO(userRepository.save(user.get()));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public List<User> getAllChildrenByParentId(String parentId) { // this is used in UserServiceTest (bc of Mockito and
                                                                  // UserDTO)
        Optional<User> parent = userRepository.findById(parentId);

        if (parent.isEmpty()) {
            throw new RuntimeException("Parent not found");
        }

        return userRepository.findAllByParentId(parentId);
    }

    public List<UserDTO> getChildrenByParentId(String parentId) {
        Optional<User> parent = userRepository.findById(parentId);

        if (parent.isEmpty()) {
            throw new RuntimeException("Parent not found");
        }
        return userRepository.findAllByParentId(parentId).stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public boolean isChildOfParent(String childId, String parentId) {
        Optional<User> child = userRepository.findById(childId);
        Optional<User> parent = userRepository.findById(parentId);

        if (parent.isEmpty()) {
            throw new RuntimeException("Child or Parent not found");
        } else if (child.isEmpty()) {
            throw new RuntimeException("Child not found");
        }

        return parentId.equals(child.get().getParentId());
    }

    public List<User> getAllChildren(String parentId) {
        Optional<User> parent = userRepository.findById(parentId);

        if (parent.isEmpty()) {
            throw new RuntimeException("Child or Parent not found");
        }

        return userRepository.findAllByParentId(parentId);
    }

    public List<UserSearchResultDTO> searchUsers(String name, String surname) {
        return userRepository.findByNameAndSurnameLike(name, surname)
                .stream()
                .map(user -> new UserSearchResultDTO(
                        user.getId(), user.getName(), user.getSurname(), user.getUsername()))
                .collect(Collectors.toList());
    }

    public double getBalanceByUserId(String userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return user.get().getBalance();
    }

    public String getUserNameAndSurnameById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getName() + " " + user.getSurname();
    }

}