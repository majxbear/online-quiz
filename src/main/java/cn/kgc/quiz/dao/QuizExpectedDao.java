package cn.kgc.quiz.dao;


import cn.kgc.quiz.domain.UserQuizExpected;

import java.util.List;

public interface QuizExpectedDao {
    void save(UserQuizExpected attempt);

    void delete(UserQuizExpected attempt);

    UserQuizExpected find(Long id);

    List<UserQuizExpected> findByUserId(Long userId);

    UserQuizExpected find(Long userId, Long quizId);

    List<UserQuizExpected> findByQuizId(Long quizId);


}
