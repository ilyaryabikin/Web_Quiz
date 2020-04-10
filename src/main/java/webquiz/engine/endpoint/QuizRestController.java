package webquiz.engine.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webquiz.engine.domain.*;
import webquiz.engine.exception.UserIsNotAuthorException;
import webquiz.engine.repository.QuizRepository;
import webquiz.engine.repository.SolvedQuizRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {
    private static final int PAGE_SIZE = 10;

    private final QuizRepository quizRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    @Autowired
    public QuizRestController(QuizRepository quizRepository,
                              SolvedQuizRepository solvedQuizRepository) {
        this.quizRepository = quizRepository;
        this.solvedQuizRepository = solvedQuizRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        quiz.setAuthor(user);
        return quizRepository.save(quiz);
    }

    @PostMapping(value = "{id}/solve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Feedback solveQuiz(@PathVariable int id,
                              @Valid @RequestBody SubmittedAnswer submittedAnswer,
                              Authentication authentication) {
        Optional<Quiz> quizToSolve = quizRepository.findById(id);
        if (quizToSolve.isPresent()) {
            Quiz quiz = quizToSolve.get();
            List<QuizAnswer> quizAnswer = quiz.getQuizAnswers();
            List<Integer> expectedAnswer = quizAnswer.stream()
                    .map(QuizAnswer::getIndex).collect(Collectors.toList());
            List<Integer> givenAnswer = submittedAnswer.getAnswer();
            Feedback feedback = getFeedback(givenAnswer, expectedAnswer);
            if (feedback.isSuccess()) {
                User user = (User)authentication.getPrincipal();
                SolvedQuiz solvedQuiz = new SolvedQuiz(quiz, user);
                solvedQuizRepository.save(solvedQuiz);
            }
            return feedback;
        } else {
            throw new NoSuchElementException("Quiz with id " + id + " does not exist.");
        }
    }

    @GetMapping
    public Page<Quiz> getAllQuizzes(@RequestParam Optional<Integer> page) {
        return quizRepository.findAll(PageRequest.of(
                page.orElse(0),
                PAGE_SIZE));
    }

    @GetMapping("/completed")
    public Page<SolvedQuiz> getAllSolvedQuizzes(@RequestParam Optional<Integer> page,
                                                Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return solvedQuizRepository.findAllBySolver(user, PageRequest.of(
                page.orElse(0),
                PAGE_SIZE,
                Sort.by("completedAt").descending()));
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
