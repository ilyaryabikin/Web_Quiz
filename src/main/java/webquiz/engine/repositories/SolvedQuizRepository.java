package webquiz.engine.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webquiz.engine.models.quizzes.SolvedQuiz;
import webquiz.engine.models.users.User;

@Repository
public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, Integer> {

    Page<SolvedQuiz> findAllBySolver(User solver, Pageable pageable);
}
