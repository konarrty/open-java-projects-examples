package com.example.tourmanagement.repository.regression;

import com.example.tourmanagement.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByName(String name);

    @Query("""
            select name from Country 
             """)
    Iterable<String> findAllCountryNames();
}
