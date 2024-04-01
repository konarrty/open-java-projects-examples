package com.example.astontaskspringrestful.service.impl;

import com.example.astontaskspringrestful.model.Country;
import com.example.astontaskspringrestful.repository.CountryRepository;
import com.example.astontaskspringrestful.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@CacheConfig(cacheNames = "defaultCache")
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Optional<Collection<Country>> getAllCountries() {
        return Optional.of(countryRepository.findAll());
    }

    @CachePut(key = "#result.id")
    @Override
    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    @CachePut(key = "#id")
    @Override
    public Country patchCountry(Country newCountry, Long id) {
        return null;
    }

    @Cacheable(key = "#id")
    @Override
    public Optional<Country> getCountryById(Long id) {

        return countryRepository.findById(id);
    }

    @CacheEvict(key = "#id")
    @Override
    public void deleteCountryById(Long id) {
        if (!countryRepository.existsById(id))
            throw new NoSuchElementException("Страна не найденj!");

        countryRepository.deleteById(id);
    }

}
