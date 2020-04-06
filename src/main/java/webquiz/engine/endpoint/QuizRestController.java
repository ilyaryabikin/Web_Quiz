package webquiz.engine.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webquiz.engine.domain.*;
import webquiz.engine.exception.UserIsNotAuthorException;
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
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        quiz.setAuthor(user);
        return quizRepository.save(quiz);
    }

    @PostMapping(value = "{id}/solve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Feedback solveQuiz(@PathVariable int id,
                              @Valid @RequestBody SubmittedAnswer submittedAnswer) {
        Optional<Quiz> quizToSolve = quizRepository.findById(id);
        if (quizToSolve.isPresent()) {
            List<Answer> quizAnswer = quizToSolve.get().getAnswers();
            List<Integer> expectedAnswer = quizAnswer.stream()
                    .map(Answer::getAnswerIndex).collect(Collectors.toList());
            List<Integer> givenAnswer = submittedAnswer.getAnswer();
            return getFeedback(givenAnswer, expectedAnswer);
        } else {
            throw new NoSuchElementException("Quiz with id " + id + " does not exist.");
        }
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @GetMapping("{id}")
    public Quiz getQuiz(@PathVariable int id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        return quiz.orElseThrow(() ->
                new NoSuchElementException("Quiz with id " + id + " does not exist."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable int id, Authentication authentication) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Quiz with id " + id + " does not exist."));
        User user = (User)authentication.getPrincipal();
        if (quiz.getAuthor().getId() != user.getId()) {
            throw new UserIsNotAuthorException("User with email " + user.getEmail() +
                    " is not allowed to delete a quiz he had not created.");
        }
        quizRepository.delete(quiz);
    }

    private Feedback getFeedback(List<Integer> givenAnswer, List<Integer> expectedAnswer) {
        if (givenAnswer.equals(expectedAnswer)) {
            return Feedback.RIGHT;
        } else {
            return Feedback.WRONG;
        }
    }
}