package webquiz.engine.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import webquiz.engine.domain.Answer;
import webquiz.engine.domain.Feedback;
import webquiz.engine.domain.Quiz;
import webquiz.engine.domain.SubmittedAnswer;
import webquiz.engine.repository.QuizRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizRestController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz) {
        quizRepository.save(quiz);
        return quiz;
    }

    @PostMapping("{id}/solve")
    public Feedback solveQuiz(@PathVariable int id,
                              @Valid @RequestBody SubmittedAnswer submittedAnswer) {
        Optional<Quiz> quizToSolve = quizRepository.findById(id);
        if (quizToSolve.isPresent()) {
            List<Integer> givenAnswer = submittedAnswer.getAnswer();
            List<Integer> expectedAnswer = quizToSolve.get().getAnswers().stream()
                    .map(Answer::getAnswerIndex).collect(Collectors.toList());
            return getFeedback(givenAnswer, expectedAnswer);
        } else {
            throw new NoSuchElementException("Quiz with id " + id + " does not exist");
        }
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @GetMapping("{id}")
    public Quiz getQuiz(@PathVariable int id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
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
