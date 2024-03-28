package com.example.aston.dao;

import com.example.aston.model.Country;

import java.util.Collection;

//@Contract
public interface CountryDAO {
    Collection<Country> getAllCountries();

    void createCountry(Country country);

    void putCountry(Country country, Long id);

    void deleteCountry(Long countryId);

    Country getCountryByName(String name);

    Country getCountryById(Long id, boolean cacheable);
}
