package cn.kgc.quiz.dao;


import cn.kgc.quiz.domain.UserQuizAttempt;

import java.util.List;

public interface QuizAttemptDao {
    void save(UserQuizAttempt attempt);

    void delete(UserQuizAttempt attempt);

    UserQuizAttempt findById(Long id);

    UserQuizAttempt findByUUID(String uuid);

    List<UserQuizAttempt> findByUserId(Long userId);

    List<UserQuizAttempt> findByQuizIdAndUserId(Long userId, Long quizId);

    List<UserQuizAttempt> findByQuizId(Long quizId);

    Integer findFinishedNum(Long quizId);

    Integer findExpectedNum(Long quizId);

    Integer findScoreNumBetweenAAndB(Long quizId, Double a, Double b);

    List findQuizResults( Long quizId);

    List findNonGroupQuizResults (Long quizId);

    List findQuizResultsLessThan(Long quizId, Double grade, Double minute);

    List findNonGroupQuizResultsLessThan( Long quizId, Double grade, Double minute);

    List findUnfinishedStu(Long quizId);


}
