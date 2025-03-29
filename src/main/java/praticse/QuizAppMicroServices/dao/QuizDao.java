package praticse.QuizAppMicroServices.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import praticse.QuizAppMicroServices.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}
