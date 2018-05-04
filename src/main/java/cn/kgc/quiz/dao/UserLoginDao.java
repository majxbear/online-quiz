package cn.kgc.quiz.dao;

import cn.kgc.quiz.domain.UserLogin;

public interface UserLoginDao {

    void save(UserLogin user);

    void delete(UserLogin user);

    UserLogin findByUserAndToken(String username, String token);

}
