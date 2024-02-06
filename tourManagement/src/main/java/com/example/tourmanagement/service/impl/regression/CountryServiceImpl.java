package com.example.tourmanagement.service.impl.regression;

import com.example.tourmanagement.model.entity.Country;
import com.example.tourmanagement.repository.regression.CountryRepository;
import com.example.tourmanagement.service.regression.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Iterable<String> getAllCountriesName() {

        return countryRepository.findAllCountryNames();
    }

    @Override
    public Country getByName(String name) {

        return countryRepository
                .findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Страна не найдена!"));

    }
}
