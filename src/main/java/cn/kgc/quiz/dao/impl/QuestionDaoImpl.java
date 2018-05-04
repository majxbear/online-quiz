package cn.kgc.quiz.dao.impl;

import cn.kgc.quiz.dao.QuestionDao;
import cn.kgc.quiz.domain.Question;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class QuestionDaoImpl implements QuestionDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private  Transaction t = null;

    public void save(Question question) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.saveOrUpdate(question);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void delete(Question question) {
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.delete(question);
            t.commit();
        } catch (Exception err) {
            t.rollback();
            err.printStackTrace();
        } finally {
            s.close();
        }
    }


    public Question findByID(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from Question as obj where obj.id= :id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
           return (Question) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }


    public List<Question> findByQuestionText(String questionText) {
        try {
            s = sessionFactory.openSession();
            String hql = "from Question as obj where obj.questionText like '%" + questionText + "%' order by obj.name";
            Query query = s.createQuery(hql);
            return query.list();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }

    public List<Question> findByName(String name) {
        try {
            s = sessionFactory.openSession();
            String hql = "from Question as obj where obj.name like '%" + name + "%' order by obj.name";
            Query query = s.createQuery(hql);
            return query.list();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }

    public Question findByUuidFlag(String uuidFlag) {
        try {
            s = sessionFactory.openSession();
            String hql = "from Question as obj where obj.uuidFlag =:uuidFlag";
            Query query = s.createQuery(hql);
            query.setParameter("uuidFlag", uuidFlag);
            return (Question) query.uniqueResult();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }

    @Override
    public List<Question> getAll() {
        try {
            s = sessionFactory.openSession();
            String hql = "from Question";
            Query query = s.createQuery(hql);
            return query.list();
        } catch (Exception err) {
        } finally {
            s.close();
        }
        return null;
    }
}
