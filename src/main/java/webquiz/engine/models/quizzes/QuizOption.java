package webquiz.engine.models.quizzes;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity(name = "options")
@Data
@NoArgsConstructor
public class QuizOption implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @NotBlank
    private String value;

    public QuizOption(String value, Quiz quiz) {
        this.value = value;
        this.quiz = quiz;
    }
}
