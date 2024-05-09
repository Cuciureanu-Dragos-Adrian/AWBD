package app.restman.api.bootstrap;

//import app.restman.api.entities.security.Authority;
//import app.restman.api.repositories.security.AuthorityRepository;
import app.restman.api.entities.security.User;
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

    //private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private void loadUserData() {
        if (userRepository.count() == 0){
            //Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            //Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_GUEST").build());

            User admin = User.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("12345"))
                    //.authority(adminRole)
                    .build();

//            User admin2 = User.builder()
//                    .username("as")
//                    .password(passwordEncoder.encode(""))
//                    .authority(adminRole)
//                    .build();

            User guest = User.builder()
                    .email("guest")
                    .password(passwordEncoder.encode("12345"))
                    //.authority(guestRole)
                    .build();

            userRepository.save(admin);
            //userRepository.save(admin2);
            userRepository.save(guest);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
