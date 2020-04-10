package webquiz.engine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webquiz.engine.models.exceptions.UserIsNotAuthorException;
import webquiz.engine.models.quizzes.Feedback;
import webquiz.engine.models.quizzes.Quiz;
import webquiz.engine.models.quizzes.SolvedQuiz;
import webquiz.engine.models.quizzes.SubmittedAnswer;
import webquiz.engine.models.users.User;
import webquiz.engine.repositories.QuizRepository;
import webquiz.engine.repositories.SolvedQuizRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        User user = getUserFromAuthentication(authentication);
        quiz.setAuthor(user);
        return quizRepository.save(quiz);
    }

    @PostMapping(value = "{id}/solve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Feedback solveQuiz(@PathVariable int id,
                              @Valid @RequestBody SubmittedAnswer submittedAnswer,
                              Authentication authentication) {
        Quiz quizToSolve = quizRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Quiz with id " + id + " does not exist."));
        Feedback feedback = quizToSolve.testAgainst(submittedAnswer);
        if (feedback.isSuccess()) {
            saveSolvedQuiz(quizToSolve, authentication);
        }
        return feedback;
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
        User user = getUserFromAuthentication(authentication);
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
        User user = getUserFromAuthentication(authentication);
        if (!user.isAuthorOf(quiz)) {
            throw new UserIsNotAuthorException("User with email " + user.getEmail() +
                    " is not allowed to delete a quiz he had not created.");
        }
        quizRepository.delete(quiz);
    }

    private SolvedQuiz saveSolvedQuiz(Quiz quiz, Authentication authentication) {
        User user = getUserFromAuthentication(authentication);
        SolvedQuiz solvedQuiz = new SolvedQuiz(quiz, user);
        return solvedQuizRepository.save(solvedQuiz);
    }

    private User getUserFromAuthentication(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
