package webquiz.engine.models.quizzes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Feedback {
    RIGHT(true,"Congratulations, you're right!"),
    WRONG(false,"Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

}
