package app.restman.api.bootstrap;

//import app.restman.api.entities.security.Authority;
//import app.restman.api.repositories.security.AuthorityRepository;
import app.restman.api.entities.security.Role;
import app.restman.api.entities.security.RoleEnum;
import app.restman.api.entities.security.User;
import app.restman.api.repositories.security.RoleRepository;
import app.restman.api.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Profile("mysql")
public class DataLoader implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private void loadUserData() {
        if (userRepository.count() == 0){
            var staffRole = roleRepository.save(new Role(RoleEnum.Staff));
            var adminRole = roleRepository.save(new Role(RoleEnum.Admin));
            var userRole = roleRepository.save(new Role(RoleEnum.User));

            User admin = User.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("12345"))
                    .fullName("admin")
                    .role(adminRole)
                    .build();

            User staff = User.builder()
                    .email("staff")
                    .password(passwordEncoder.encode("12345"))
                    .fullName("staff")
                    .role(staffRole)
                    .build();

            User guest = User.builder()
                    .email("guest")
                    .password(passwordEncoder.encode("12345"))
                    .fullName("guest")
                    .role(userRole)
                    .build();

            userRepository.save(admin);
            userRepository.save(staff);
            userRepository.save(guest);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
