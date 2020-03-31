package webquiz.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webquiz.engine.domain.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
