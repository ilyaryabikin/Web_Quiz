package webquiz.engine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import webquiz.engine.json.QuizDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@JsonDeserialize(using = QuizDeserializer.class)
public class Quiz {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @Size(min = 2)
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answerList;

    public Quiz(String title, String text, List<String> options, List<Integer> answerList) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answerList = answerList;
    }

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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswerList() {
        return answerList;
    }

    @JsonSetter("answer")
    public void setAnswerList(List<Integer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, options, answerList);
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
                answerList.equals(other.answerList);
    }
}
