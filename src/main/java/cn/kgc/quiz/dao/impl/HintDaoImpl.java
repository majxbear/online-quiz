package cn.kgc.quiz.dao.impl;

import cn.kgc.quiz.dao.HintDao;
import cn.kgc.quiz.domain.Hint;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HintDaoImpl implements HintDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private Transaction t = null;

    public void save(Hint hint) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.saveOrUpdate(hint);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void delete(Hint hint) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.delete(hint);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void deleteByQuestionId(Long questionId) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            List<Hint> hintList = this.findByQuestionId(questionId);
            for (Hint hint : hintList) {
                delete(hint);
            }
            t.commit();
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
        }
    }

    public List<Hint> findByQuestionId(Long questionId) {
        try {
            s = sessionFactory.openSession();
            String hql = " from Hint as obj where obj.questionId=:questionId  order by obj.hintOrder";
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

    public Hint findByID(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from Hint as obj where obj.id= :id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
            return (Hint) query.uniqueResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;

    }
}
