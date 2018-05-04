package cn.kgc.quiz.dao.impl;

import cn.kgc.quiz.dao.QuestionAttemptDao;
import cn.kgc.quiz.domain.UserQuestionAttempt;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class QuestionAttemptDaoImpl implements QuestionAttemptDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private Transaction t = null;
    public void save(UserQuestionAttempt attempt) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.saveOrUpdate(attempt);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void delete(UserQuestionAttempt attempt) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.delete(attempt);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public UserQuestionAttempt findByUUID(String uuid) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuestionAttempt as obj where obj.uuidFlag =:uuidFlag";
            Query query = s.createQuery(hql);
            return (UserQuestionAttempt) query.uniqueResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }

    public List<UserQuestionAttempt> findByAttemptId(Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuestionAttempt as obj where obj.userQuizAttemptId=:userQuizAttemptId order by obj.questionId";
            Query query = s.createQuery(hql);
            query.setParameter("userQuizAttemptId", quizId);
            return query.list();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }

    public UserQuestionAttempt findById(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuestionAttempt as obj where obj.id= :id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
            return (UserQuestionAttempt) query.uniqueResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }

    public UserQuestionAttempt findByQuizAttemptIdAndQuestionId(Long quizAttemptId, Long questionId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuestionAttempt as obj where obj.userQuizAttemptId=:userQuizAttemptId and obj.questionId=:questionId";
            Query query = s.createQuery(hql);
            query.setParameter("userQuizAttemptId", quizAttemptId);
            query.setParameter("questionId", questionId);
            return (UserQuestionAttempt) query.uniqueResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }

    public Integer findAnsweredSum(Long quizId,Long questionId){
        try {
            s = sessionFactory.openSession();
            String hql = "select count(*) from UserQuestionAttempt q1,UserQuizAttempt q2 where q1.userQuizAttemptId =q2.id and q2.quizId=:quizId and q1.questionId=:questionId";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("questionId", questionId);
            return query.getFirstResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return 0;

    }

    public Integer findWrongNum(Long quizId,Long questionId){
        try {
            s = sessionFactory.openSession();
            String hql = "select count(*) from UserQuestionAttempt q1,UserQuizAttempt q2 where q1.userQuizAttemptId =q2.id and q2.quizId=:quizId and q1.resultFlag=2 and q1.questionId=:questionId";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("questionId", questionId);
            return query.getFirstResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return 0;
    }

    public Integer findItemAnsweredNum(Long quizId,Long questionId,Integer type,Long answerId) {
        try {
            s = sessionFactory.openSession();
            String hql;
            if(type==0)
                hql = "select count(*) from UserQuestionAttempt q1, UserQuizAttempt q2  where q1.userQuizAttemptId =q2.id and q2.quizId=:quizId and q1.questionId=:questionId  and to_number(q1.userAnswer)=? ";
            else
                hql = "select count(*) from UserQuestionAttempt q1, UserQuizAttempt q2  where q1.userQuizAttemptId =q2.id and q2.quizId=:quizId and q1.questionId=:questionId  and q1.userAnswer like '%" + answerId + "%' ";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("questionId", questionId);
            return query.getFirstResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return 0;
    }

    public Integer findItemAnsweredNumWithCertainty(Long quizId, Long questionId, Integer type, Integer certainty, Long answerId) {
        try {
            s = sessionFactory.openSession();
            String hql;
            if(type==0)
                hql = "select count(*) from UserQuestionAttempt q1, UserQuizAttempt q2  where q1.userQuizAttemptId =q2.id and q2.quizId=:quizId and q1.questionId=:questionId  and q1.certainty=:certainty and to_number(q1.userAnswer)=? ";
            else
                hql = "select count(*) from UserQuestionAttempt q1, UserQuizAttempt q2  where q1.userQuizAttemptId =q2.id and q2.quizId=:quizId and q1.questionId=:questionId  and q1.certainty=:certainty and q1.userAnswer like '%" + answerId + "%' ";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("questionId", questionId);
            query.setParameter("certainty", certainty);
            return query.getFirstResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return 0;

    }

    public List findUserIdeas(Long quizId, Long questionId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuestionAttempt q1, UserQuizAttempt q2 where q1.userQuizAttemptId =q2.id and q2.quizId=:quizId and q1.questionId=:questionId and q1.userIdea is not null";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("questionId", questionId);
            return query.list();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }


}
