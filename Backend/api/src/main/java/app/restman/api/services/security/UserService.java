package app.restman.api.services.security;

import app.restman.api.entities.security.User;
import app.restman.api.repositories.security.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("mysql")
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getByUsername(String username){
        return userRepository.findAll().stream().filter(user -> user.getFullName().equals(username)).findFirst().orElse(null);
    }
}
