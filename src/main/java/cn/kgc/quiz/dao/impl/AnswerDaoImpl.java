package cn.kgc.quiz.dao.impl;


import cn.kgc.quiz.dao.AnswerDao;
import cn.kgc.quiz.domain.QuestionAnswer;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AnswerDaoImpl implements AnswerDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private Transaction t = null;

    public void save(QuestionAnswer answer) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.saveOrUpdate(answer);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void delete(QuestionAnswer answer) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.delete(answer);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public QuestionAnswer findByID(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from QuestionAnswer as obj where obj.id= :id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
            return (QuestionAnswer) query.uniqueResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }

    public List<QuestionAnswer> findRightAnswer(Long questionId) {
        try {
            s = sessionFactory.openSession();
            String hql = " from QuestionAnswer as obj where obj.questionId=:questionId  and obj.rightAnswer=0 order by obj.answerOrder asc";
            Query query = s.createQuery(hql);
            query.setParameter("questionId", questionId);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public List<QuestionAnswer> findByQuestionId(Long questionId) {
        try {
            s = sessionFactory.openSession();
            String hql = " from QuestionAnswer as obj where obj.questionId=:questionId  order by obj.answerOrder asc";
            Query query = s.createQuery(hql);
            query.setParameter("questionId", questionId);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;

    }
}
