package cn.kgc.quiz.dao;


import cn.kgc.quiz.domain.UserQuestionAttempt;

import java.util.List;

public interface QuestionAttemptDao {
     void save(UserQuestionAttempt attempt);

     void delete(UserQuestionAttempt attempt);

     UserQuestionAttempt findById(Long id);

     List<UserQuestionAttempt> findByAttemptId(Long quizId);

     UserQuestionAttempt findByQuizAttemptIdAndQuestionId(Long quizAttemptId, Long questionId);

     Integer findAnsweredSum(Long quizId, Long questionId);

     Integer findWrongNum(Long quizId, Long questionId);

     Integer findItemAnsweredNum(Long quizId, Long questionId, Integer type, Long answerId);

     Integer findItemAnsweredNumWithCertainty(Long quizId, Long questionId, Integer type, Integer certainty, Long answerId);

     List findUserIdeas(Long quizId, Long questionId);

     UserQuestionAttempt findByUUID(String uuid);


}
