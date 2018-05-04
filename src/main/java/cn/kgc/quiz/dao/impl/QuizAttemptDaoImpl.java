package cn.kgc.quiz.dao.impl;

import cn.kgc.quiz.dao.QuizAttemptDao;
import cn.kgc.quiz.domain.UserQuizAttempt;
import cn.kgc.quiz.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class QuizAttemptDaoImpl implements QuizAttemptDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session s = null;
    private Transaction t = null;

    public void save(UserQuizAttempt attempt) {
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

    public void delete(UserQuizAttempt attempt) {
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

    public UserQuizAttempt findByUUID(String uuid) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizAttempt as obj where obj.uuidFlag =:uuidFlag";
            Query query = s.createQuery(hql);
            query.setParameter("uuidFlag", uuid);
            return (UserQuizAttempt) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    @Override
    public List<UserQuizAttempt> findByUserId(Long userId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizAttempt as obj where obj.quizUserId=:quizUserId order by obj.quizAttemptSeq";
            Query query = s.createQuery(hql);
            query.setParameter("quizUserId", userId);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public List<UserQuizAttempt> findByQuizIdAndUserId(Long userId, Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizAttempt as obj where obj.quizUserId=:quizUserId and obj.quizId=:quizId order by obj.quizAttemptSeq";
            Query query = s.createQuery(hql);
            query.setParameter("quizUserId", userId);
            query.setParameter("quizId", quizId);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public List<UserQuizAttempt> findByQuizId(Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizAttempt as obj where obj.quizId=:quizId order by obj.sumGrades desc, obj.quizUserId";
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

    public UserQuizAttempt findById(Long id) {
        try {
            s = sessionFactory.openSession();
            String hql = "from UserQuizAttempt as obj where obj.id=:id";
            Query query = s.createQuery(hql);
            query.setParameter("id", id);
            return (UserQuizAttempt) query.uniqueResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;

    }

    public Integer findFinishedNum(Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "select count(distinct obj.quizUserId) from UserQuizAttempt as obj where obj.quizId=:quizId";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            return query.getFirstResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return 0;
    }

    public Integer findExpectedNum(Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "select count(*) from UserQuizAttempt as obj where obj.quizId=:quizId";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            return query.getFirstResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return 0;
    }

    public Integer findScoreNumBetweenAAndB(Long quizId, Double a, Double b) {
        try {
            s = sessionFactory.openSession();
            String hql = "select count(*) from UserQuizAttempt as obj where obj.quizId=:quizId and obj.sumGrades between :a and :b";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("a", a);
            query.setParameter("b", b);
            return query.getFirstResult();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return 0;
    }

    @Override
    public List findQuizResults(Long quizId) {
        return null;
    }


    public List findNonGroupQuizResults(Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "select q.id,u.username,u.realname,q.sumGrades,q.state,q.timeStart,q.timeFinish," +
                    "to_char(to_number(q.timeFinish-q.timeStart)*24*60,'fm9999990.00') " +
                    "from UserQuizAttempt q, QuizUser u  " +
                    "where q.quizUserId=u.id " +
                    "and q.quizUserId=t.userId " +
                    "and q.quizId=:quizId " +
                    "order by q.sumGrades desc";
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

    @Override
    public List findQuizResultsLessThan(Long quizId, Double grade, Double minute) {
        return null;
    }


    public List findNonGroupQuizResultsLessThan(Long quizId, Double grade, Double minute) {
        try {
            s = sessionFactory.openSession();
            String hql = "select q.id,u.username,u.realname,q.sumGrades,q.state,q.timeStart,q.timeFinish," +
                    "to_char(to_number(q.timeFinish-q.timeStart)*24*60,'fm9999990.00') " +
                    "from UserQuizAttempt q, QuizUser u " +
                    "where q.quizUserId=u.id " +
                    "and q.quizUserId=t.userId " +
                    "and q.sumGrades<:grade " +
                    "and to_number(q.timeFinish-q.timeStart)*24*60<=" + minute + " " +
                    "and q.quizId=:quizId " +
                    "order by q.sumGrades desc";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("grade", grade);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    public List findUnfinishedStu(Long quizId) {
        try {
            s = sessionFactory.openSession();
            String hql = "select u.id,u.userName,u.realName,u.email from UserQuizExpected e,QuizUser u  " +
                    "where e.quizUserId=u.id " +
                    "and e.quizUserId not in " +
                    "(select distinct q.quizUserId from UserQuizAttempt q " +
                    "where e.quizId=q.quizId and e.quizUserId=q.quizUserId  and q.quizId=:quizId)" +
                    "and e.quizId=:quizId " +
                    "order by u.username";
            Query query = s.createQuery(hql);
            query.setParameter("quizId", quizId);
            query.setParameter("quizId", quizId);
            return query.list();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }
}
