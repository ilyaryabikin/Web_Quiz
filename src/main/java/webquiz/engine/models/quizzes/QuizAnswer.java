package webquiz.engine.models.quizzes;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "answers")
@Data
@NoArgsConstructor
public class QuizAnswer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "index")
    private int index;

    public QuizAnswer(int index, Quiz quiz) {
        this.index = index;
        this.quiz = quiz;
    }
}
