package webquiz.engine.models.quizzes;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import webquiz.engine.models.quizzes.json.SolvedQuizSerializer;
import webquiz.engine.models.users.User;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "solved_quizzes")
@JsonSerialize(using = SolvedQuizSerializer.class)
@Data
public class SolvedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Instant completedAt;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "solver_id")
    private User solver;

    public SolvedQuiz() {
        this.completedAt = Instant.now();
    }

    public SolvedQuiz(Quiz quiz, User solver) {
        this();
        this.quiz = quiz;
        this.solver = solver;
    }
}
