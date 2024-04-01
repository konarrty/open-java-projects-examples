package com.example.astontaskspringrestful.service;

import com.example.astontaskspringrestful.model.Country;

import java.util.Collection;
import java.util.Optional;

public interface CountryService {
    Optional<Collection<Country>> getAllCountries();

    Optional<Country> getCountryById(Long id);

    void deleteCountryById(Long id);

    Country createCountry(Country country);

    Country patchCountry(Country newCountry, Long id);

}
