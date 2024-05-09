package app.restman.api.services.security;

import app.restman.api.DTOs.LoginUserDto;
import app.restman.api.DTOs.RegisterUserDto;
import app.restman.api.entities.security.RoleEnum;
import app.restman.api.entities.security.User;
import app.restman.api.repositories.security.RoleRepository;
import app.restman.api.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("mysql")
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signupRegularUser(RegisterUserDto input) throws Exception {
        validateRegisterData(input);

        return signupUser(input, RoleEnum.User);
    }

    public User signupStaff(RegisterUserDto input) throws Exception {
        validateRegisterData(input);

        return signupUser(input, RoleEnum.Staff);
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

    private void validateRegisterData(RegisterUserDto registerDto) throws Exception {
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent())
            throw new Exception("User with given email already exists!");

        if (userService.getByUsername(registerDto.getUsername()) != null)
            throw new Exception("Username is taken!");

        if (registerDto.getPassword().length() <= 4)
            throw new Exception("Password must have at least 5 characters!");
    }

    private User signupUser(RegisterUserDto registerDto, RoleEnum role){
        User user = User.builder()
                .fullName(registerDto.getUsername())
                .email(registerDto.getEmail())
                .role(roleRepository.findById(role).orElse(null))
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();

        return userRepository.save(user);
    }
}
