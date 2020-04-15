package webquiz.engine.models.quizzes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import webquiz.engine.models.quizzes.json.QuizDeserializer;
import webquiz.engine.models.quizzes.json.QuizSerializer;
import webquiz.engine.models.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity(name = "quizzes")
@JsonSerialize(using = QuizSerializer.class)
@JsonDeserialize(using = QuizDeserializer.class)
@Data
@NoArgsConstructor
public class Quiz implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Size(min = 2)
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizOption> quizOptions;

    @NotNull
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizAnswer> quizAnswers;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<SolvedQuiz> solvedQuizzes;

    public Feedback testAgainst(SubmittedAnswer submittedAnswer) {
        List<Integer> expectedAnswer = quizAnswers.stream()
                .map(QuizAnswer::getIndex).collect(Collectors.toList());
        List<Integer> givenAnswer = submittedAnswer.getAnswer();
        return expectedAnswer.equals(givenAnswer) ? Feedback.RIGHT :
                Feedback.WRONG;
    }
}
