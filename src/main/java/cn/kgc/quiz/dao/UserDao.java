package cn.kgc.quiz.dao;

import cn.kgc.quiz.domain.QuizUser;

import java.util.List;

public interface UserDao {

    void save(QuizUser user);

    void delete(QuizUser user);

    QuizUser get(Long id);

    QuizUser getByUsernameAndPassword(String username, String password);

    List<QuizUser> getAll();


}
