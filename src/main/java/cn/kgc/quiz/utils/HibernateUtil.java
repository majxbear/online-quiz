package cn.kgc.quiz.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry standardServiceRegistry;

    static {
        try {
            //��һ��:��ȡHibernate�������ļ�  hibernamte.cfg.xml�ļ�
            configuration = new Configuration().configure("hibernate.cfg.xml");
            //�ڶ���:��������ע�ṹ��������ͨ�����ö����м������е�������Ϣ
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(configuration.getProperties());
            //����ע�����
            standardServiceRegistry = builder.build();
            //������:�����Ự����
            sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

}
