package webquiz.engine.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Answer {

    @NotNull
    private List<Integer> answerList;

    public Answer() { }

    public Answer(List<Integer> answerList) {
        this.answerList = answerList;
    }

    @JsonGetter("answer")
    public List<Integer> getAnswerList() {
        return answerList;
    }

    @JsonSetter("answer")
    public void setAnswerList(List<Integer> answerList) {
        this.answerList = answerList;
    }
}
