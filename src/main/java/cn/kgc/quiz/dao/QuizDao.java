package cn.kgc.quiz.dao;


import cn.kgc.quiz.domain.Quiz;
import cn.kgc.quiz.domain.QuizQuestion;

import java.util.List;

public interface QuizDao {
    void save(Quiz quiz);

    void delete(Quiz quiz);

    Quiz findByID(Long id);

    Quiz findByUUID(String uuid);

    void saveQuestion(QuizQuestion qq);

    void deleteQuestion(QuizQuestion qq);

    List<QuizQuestion> findByQuizId(Long quizId);

    QuizQuestion findById(Long id);

    QuizQuestion findByQuizIdAndQuestionId(Long quizId, Long questionId);

    List<Quiz> findAll();


}
