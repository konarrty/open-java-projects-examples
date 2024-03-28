package com.example.aston.dao.impl;

import com.example.aston.dao.CountryDAO;
import com.example.aston.model.Country;
import com.example.aston.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;

public class CountryDAOImpl implements CountryDAO {
    @Override
    public Collection<Country> getAllCountries() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Country c", Country.class)
                    .list();
        }
    }

    @Override
    public void createCountry(Country country) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.persist(country);
        }
    }

    @Override
    public void putCountry(Country country, Long id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            country.setId(id);
            Transaction transaction = session.beginTransaction();
            session.merge(country);
            transaction.commit();
        }
    }

    @Override
    public void deleteCountry(Long countryId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createQuery("delete from Country c where id = :id", Void.class)
                    .setParameter("id", countryId)
                    .executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public Country getCountryByName(String name) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Country c WHERE c.name = :name", Country.class)
                    .setParameter("name", name)
                    .setCacheable(true)
                    .setCacheRegion("countries")
                    .getSingleResult();
        }
    }

    @Override
    public Country getCountryById(Long id, boolean cacheable) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Country c WHERE c.id = :id", Country.class)
                    .setParameter("id", id)
                    .setCacheable(cacheable)
                    .setCacheRegion("countries")
                    .getSingleResult();

        }
    }

}
