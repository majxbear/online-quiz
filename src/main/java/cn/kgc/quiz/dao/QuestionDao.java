package cn.kgc.quiz.dao;


import cn.kgc.quiz.domain.Question;

import java.util.List;

public interface QuestionDao {
    void save(Question question);

    void delete(Question question);

    Question findByID(Long id);

    List<Question> findByQuestionText(String questionText);

    List<Question> findByName(String name);

    Question findByUuidFlag(String uuidFlag);

    List<Question> getAll();
}
