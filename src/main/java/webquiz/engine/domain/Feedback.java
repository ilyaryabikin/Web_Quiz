package webquiz.engine.domain;

public class Feedback {
    public static final String RIGHT = "Congratulations, you're right!";
    public static final String WRONG = "Wrong answer! Please, try again.";

    private final boolean success;
    private final String feedback;

    public Feedback(boolean success, String feedback) {
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
