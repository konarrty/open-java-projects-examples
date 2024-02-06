package com.example.tourmanagement.service.regression;

import com.example.tourmanagement.model.entity.Country;

public interface CountryService {
    Iterable<String> getAllCountriesName();

    Country getByName(String name);
}
