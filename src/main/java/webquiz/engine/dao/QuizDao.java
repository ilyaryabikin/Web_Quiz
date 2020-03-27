package webquiz.engine.dao;

import webquiz.engine.domain.Quiz;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("QuizDao")
public class QuizDao implements Dao<Quiz> {
    private static int QUIZ_ID = 1;

    private final List<Quiz> quizzes = new ArrayList<>();

    @Override
    public Optional<Quiz> get(int id) {
        return quizzes.stream()
                .filter(quiz -> quiz.getId() == id)
                .findFirst();
    }

    @Override
    public List<Quiz> getAll() {
        return quizzes;
    }

    @Override
    public void save(Quiz quiz) {
        quiz.setId(QUIZ_ID++);
        quizzes.add(quiz);
    }

    @Override
    public void update(Quiz quiz) {
        int index = quizzes.indexOf(quiz);
        quizzes.set(index, quiz);
    }

    @Override
    public void delete(Quiz quiz) {
        quizzes.remove(quiz);
    }
}
