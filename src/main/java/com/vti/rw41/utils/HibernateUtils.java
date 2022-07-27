package com.vti.rw41.utils;

import com.vti.rw41.entity.*;
import com.vti.rw41.entity.test.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
    public static SessionFactory sessionFactory = null;
    private static Session session = null;

    public static Session getSession() {
        if (session != null) {
            return session;
        }
        session = getSessionFactory().openSession();
        return session;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Department.class);
        configuration.addAnnotatedClass(ProductEntity.class);
        configuration.addAnnotatedClass(TestTable.class);
        configuration.addAnnotatedClass(StudentEntity.class);
        configuration.addAnnotatedClass(BillDetail.class);
        configuration.addAnnotatedClass(CategoryEntity.class);
        configuration.addAnnotatedClass(DetailDepartment.class);
        configuration.addAnnotatedClass(Address.class);




        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(registry);
        return sessionFactory;
    }
}
