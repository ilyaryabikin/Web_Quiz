package webquiz.engine.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import webquiz.engine.json.SolvedQuizSerializer;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "solved_quizzes")
@JsonSerialize(using = SolvedQuizSerializer.class)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getSolver() {
        return solver;
    }

    public void setSolver(User solver) {
        this.solver = solver;
    }
}
