package webquiz.engine.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import webquiz.engine.json.QuizDeserializer;
import webquiz.engine.json.QuizSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(name = "quizzes")
@JsonSerialize(using = QuizSerializer.class)
@JsonDeserialize(using = QuizDeserializer.class)
public class Quiz implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private List<Option> options;

    @NotNull
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<SolvedQuiz> solvedQuizzes;

    public Quiz() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answerList) {
        this.answers = answerList;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<SolvedQuiz> getSolvedQuizzes() {
        return solvedQuizzes;
    }

    public void setSolvedQuizzes(List<SolvedQuiz> solvedQuizzes) {
        this.solvedQuizzes = solvedQuizzes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, options, answers);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quiz other = ((Quiz) obj);
        return id == other.id &&
                title.equals(other.title) &&
                text.equals(other.text) &&
                options.equals(other.options) &&
                answers.equals(other.answers);
    }
}
