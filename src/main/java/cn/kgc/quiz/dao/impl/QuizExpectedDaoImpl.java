package cn.kgc.quiz.dao.impl;

import cn.kgc.quiz.dao.QuizExpectedDao;
import cn.kgc.quiz.domain.UserQuizExpected;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class QuizExpectedDaoImpl implements QuizExpectedDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private Transaction t = null;

    public void save(UserQuizExpected attempt) {
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

    public void delete(UserQuizExpected attempt) {
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


    public UserQuizExpected find(Long userId, Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizExpected as obj where obj.quizUserId=:userId and  obj.quizId=:quizId";
            Query query = s.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("quizId", quizId);
            return (UserQuizExpected) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }


    public List<UserQuizExpected> findByUserId(Long userId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizExpected as obj where obj.quizUserId=:userId order by  obj.quizId";
            Query query = s.createQuery(hql);
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public List<UserQuizExpected> findByQuizId(Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizExpected as obj where obj.quizId=:quizId";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public UserQuizExpected find(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizExpected as obj where obj.id=:id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
            return (UserQuizExpected) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

}
