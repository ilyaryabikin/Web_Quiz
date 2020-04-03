package webquiz.engine.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webquiz.engine.domain.User;
import webquiz.engine.domain.UserCredentials;
import webquiz.engine.exception.UserAlreadyExistsException;
import webquiz.engine.repository.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/register")
public class UserRestController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerNewUser(@Valid @RequestBody UserCredentials userCredentials) {
        Optional<User> existedUser = userRepository.findUserByEmail(userCredentials.getEmail());
        if (existedUser.isEmpty()) {
            userRepository.save(userCredentials.toUser(passwordEncoder));
        } else {
            throw new UserAlreadyExistsException("User with email " +
                    userCredentials.getEmail() + " already exists.");
        }
    }
}