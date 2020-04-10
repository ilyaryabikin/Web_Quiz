package webquiz.engine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webquiz.engine.models.quizzes.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
