package cn.kgc.quiz.dao;


import cn.kgc.quiz.domain.Hint;

import java.util.List;

public interface HintDao {
    void save(Hint hint);

    void delete(Hint hint);

    List<Hint> findByQuestionId(Long questionId);

    Hint findByID(Long id);

}
