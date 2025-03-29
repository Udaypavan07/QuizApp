package praticse.QuizAppMicroServices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import praticse.QuizAppMicroServices.dao.QuestionDao;
import praticse.QuizAppMicroServices.dao.QuizDao;
import praticse.QuizAppMicroServices.model.Question;
import praticse.QuizAppMicroServices.model.QuestionWrapper;
import praticse.QuizAppMicroServices.model.Quiz;
import praticse.QuizAppMicroServices.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Sucess", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questions=quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for(Question q:questions){
            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(questionWrapper);
        }

        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int rightAns=0,i=0;

        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())) rightAns++;
            i++;
        }

        return new ResponseEntity<>(rightAns,HttpStatus.OK);

    }
}
