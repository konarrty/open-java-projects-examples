package com.example.astontaskspringrestful.service;

import com.example.astontaskspringrestful.dto.TemperatureDTO;

import java.util.Optional;

public interface WeatherService {
    Optional<TemperatureDTO> getTemperatureByCountryId(Long id);
}
