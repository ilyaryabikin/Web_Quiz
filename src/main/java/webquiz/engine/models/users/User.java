package webquiz.engine.models.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import webquiz.engine.models.quizzes.Quiz;
import webquiz.engine.models.quizzes.SolvedQuiz;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Quiz> createdQuizzes;

    @OneToMany(mappedBy = "solver", cascade = CascadeType.ALL)
    private List<SolvedQuiz> solvedQuizzes;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isAuthorOf(Quiz quiz) {
        return Integer.compare(quiz.getId(), id) == 0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}