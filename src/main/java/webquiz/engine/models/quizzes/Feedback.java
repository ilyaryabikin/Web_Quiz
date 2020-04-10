package webquiz.engine.models.quizzes;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Feedback {
    RIGHT(true,"Congratulations, you're right!"),
    WRONG(false,"Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    Feedback(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
