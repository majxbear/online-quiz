package cn.kgc.quiz.dao.impl;

import cn.kgc.quiz.dao.QuizDao;
import cn.kgc.quiz.domain.Quiz;
import cn.kgc.quiz.domain.QuizQuestion;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class QuizDaoImpl implements QuizDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private Transaction t = null;
    public void save(Quiz quiz) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.saveOrUpdate(quiz);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void delete(Quiz quiz) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.delete(quiz);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }


    public Quiz findByID(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from Quiz as obj where obj.id= :id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
            return (Quiz) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }


    public Quiz findByUUID(String uuid) {
        try {
            s = sessionFactory.openSession();
            String hql = "from Quiz as obj where obj.uuidFlag =:uuidFlag";
            Query query = s.createQuery(hql);
            return (Quiz) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public void saveQuestion(QuizQuestion qq) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.saveOrUpdate(qq);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void deleteQuestion(QuizQuestion qq) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.delete(qq);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public List<QuizQuestion> findByQuizId(Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from QuizQuestion as obj where obj.quizId=:quizId order by obj.questionId";
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

    public QuizQuestion findById(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from QuizQuestion as obj where obj.id= :id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
            return (QuizQuestion) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public QuizQuestion findByQuizIdAndQuestionId(Long quizId, Long questionId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from QuizQuestion as obj where obj.quizId=:quizId and and obj.questionId=:questionId";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("questionId", questionId);
            return (QuizQuestion) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public List<Quiz> findAll() {
        try {
            s = sessionFactory.openSession();
            String hql = "from Quiz";
            Query query = s.createQuery(hql);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }
}
