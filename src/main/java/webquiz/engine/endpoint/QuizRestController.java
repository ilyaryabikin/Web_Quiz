package webquiz.engine.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import webquiz.engine.dao.Dao;
import webquiz.engine.domain.Answer;
import webquiz.engine.domain.Feedback;
import webquiz.engine.domain.Quiz;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {

    private final Dao<Quiz> quizDao;

    @Autowired
    public QuizRestController(@Qualifier("QuizDao") Dao<Quiz> quizDao) {
        this.quizDao = quizDao;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz) {
        quizDao.save(quiz);
        return quiz;
    }

    @PostMapping("{id}/solve")
    public Feedback solveQuiz(@PathVariable int id,
                              @Valid @RequestBody Answer answer) {
        Optional<Quiz> quizToSolve = quizDao.get(id);
        if (quizToSolve.isPresent()) {
            List<Integer> givenAnswer = answer.getAnswerList();
            List<Integer> expectedAnswer = quizToSolve.get().getAnswerList();
            return getFeedback(givenAnswer, expectedAnswer);
        } else {
            throw new NoSuchElementException("Quiz with id " + id + " does not exist");
        }
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizDao.getAll();
    }

    @GetMapping("{id}")
    public Quiz getQuiz(@PathVariable int id) {
        Optional<Quiz> quiz = quizDao.get(id);
        if (quiz.isPresent()) {
            return quiz.get();
        } else {
            throw new NoSuchElementException("Quiz with id " + id + " does not exist");
        }
    }

    private Feedback getFeedback(List<Integer> givenAnswer, List<Integer> expectedAnswer) {
        if (givenAnswer.equals(expectedAnswer)) {
            return new Feedback(true, Feedback.RIGHT);
        } else {
            return new Feedback(false, Feedback.WRONG);
        }
    }
}
