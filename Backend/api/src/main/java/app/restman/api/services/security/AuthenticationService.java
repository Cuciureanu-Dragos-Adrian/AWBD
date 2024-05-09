package app.restman.api.services.security;

import app.restman.api.DTOs.LoginUserDto;
import app.restman.api.DTOs.RegisterUserDto;
import app.restman.api.entities.security.User;
import app.restman.api.repositories.security.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("mysql")
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            UserService userService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public User signup(RegisterUserDto input) throws Exception {
        if (userRepository.findByEmail(input.getEmail()).isPresent())
            throw new Exception("User with given email already exists!");

        if (userService.getByUsername(input.getUsername()) != null)
            throw new Exception("Username is taken!");

        if (input.getPassword().length() <= 4)
            throw new Exception("Password must have at least 5 characters!");

        User user = User.builder()
                .fullName(input.getUsername())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
