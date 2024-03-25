package com.example.aston.utils;

import com.example.aston.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(Country.class);
                Properties p = new Properties();
                p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
                configuration.setProperties(p);
                sessionFactory = configuration.buildSessionFactory();

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return sessionFactory;
    }
}