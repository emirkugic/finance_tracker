package ba.edu.ibu.finance_tracker.core.service;

import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.LoginDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.LoginRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserDTO;
import ba.edu.ibu.finance_tracker.rest.dto.UserDTO.UserRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO signUp(UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword()));
        User user = userRepository.save(userRequestDTO.toEntity());

        return new UserDTO(user);
    }

    public LoginDTO signIn(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("This user does not exist."));
        String jwt = jwtService.generateToken(user);

        return new LoginDTO(jwt);
    }

}