package cn.kgc.quiz.dao.impl;

import cn.kgc.quiz.dao.UserDao;
import cn.kgc.quiz.domain.QuizUser;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private  Transaction t = null;

    public void save(QuizUser user) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.saveOrUpdate(user);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void delete(QuizUser user) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.delete(user);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }


    public QuizUser get(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from QuizUser as u where u.id= :id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
            return (QuizUser) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public QuizUser getByUsernameAndPassword(String username, String password) {
        try {
            s = sessionFactory.openSession();
            String hql = "from QuizUser as u where u.username=:username and u.password =:password";
            Query query = s.createQuery(hql);
            query.setParameter("username", username);
            query.setParameter("password", password);
           return  (QuizUser) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public List<QuizUser> getAll() {
        try {
            s = sessionFactory.openSession();
            String hql = " from QuizUser u where u.type=1";
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
