package webquiz.engine.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "answers")
public class QuizAnswer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "index")
    private int index;

    public QuizAnswer() {}

    public QuizAnswer(int index, Quiz quiz) {
        this.index = index;
        this.quiz = quiz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int answerIndex) {
        this.index = answerIndex;
    }
}
