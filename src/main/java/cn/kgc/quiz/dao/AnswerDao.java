package cn.kgc.quiz.dao;


import cn.kgc.quiz.domain.QuestionAnswer;

import java.util.List;

public interface AnswerDao {
     void save(QuestionAnswer answer);

     void delete(QuestionAnswer answer);

     QuestionAnswer findByID(Long id);

     List<QuestionAnswer> findRightAnswer(Long questionId);

     List<QuestionAnswer> findByQuestionId(Long questionId);

}
