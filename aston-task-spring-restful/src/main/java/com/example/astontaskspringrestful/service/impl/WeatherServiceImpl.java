package com.example.astontaskspringrestful.service.impl;

import com.example.astontaskspringrestful.api.TemperatureApi;
import com.example.astontaskspringrestful.dto.TemperatureDTO;
import com.example.astontaskspringrestful.service.CountryService;
import com.example.astontaskspringrestful.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final CountryService countryService;
    private final TemperatureApi temperatureApi;

    @Override
    public Optional<TemperatureDTO> getTemperatureByCountryId(Long id) {

        return countryService.getCountryById(id)
                .map(temperatureApi::getTemperatureByCountry);
    }
}
