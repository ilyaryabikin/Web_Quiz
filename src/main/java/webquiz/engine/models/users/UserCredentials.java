package webquiz.engine.models.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
public class UserCredentials {

    @Pattern(regexp = "\\w+@\\w+\\.\\w+")
    private String email;

    @Size(min = 5)
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(email, passwordEncoder.encode(password));
    }
}
