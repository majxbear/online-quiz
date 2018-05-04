package cn.kgc.quiz.dao.impl;

import cn.kgc.quiz.dao.UserLoginDao;
import cn.kgc.quiz.domain.UserLogin;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserLoginDaoImpl implements UserLoginDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private Transaction t = null;

    public void save(UserLogin userLogin) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.saveOrUpdate(userLogin);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void delete(UserLogin user) {
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

    public UserLogin findByUserAndToken(String username, String token) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserLogin as u where u.username =:username and u.token=:token";
            Query query = s.createQuery(hql);
            query.setParameter("username", username);
            query.setParameter("token", token);
            return (UserLogin) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }
}
