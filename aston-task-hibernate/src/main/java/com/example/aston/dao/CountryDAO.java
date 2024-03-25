package com.example.aston.dao;

import com.example.aston.model.Country;
import com.example.aston.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import java.util.Collection;

public class CountryDAO {
    public Collection<Country> getAllCountries() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Country c", Country.class)
                    .list();
        }
    }

    public Country getCountryByName(String name) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Country c WHERE c.name = :name", Country.class)
                    .setParameter("name", name)
                    .getSingleResult();

        }

    }
}
