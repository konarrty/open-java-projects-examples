package com.example.astontaskspringrestful.api;

import com.example.astontaskspringrestful.dto.TemperatureDTO;
import com.example.astontaskspringrestful.model.Country;
import com.example.astontaskspringrestful.util.TemperatureUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TemperatureApi {

    private final RestTemplate restTemplate;

    public TemperatureDTO getTemperatureByCountry(Country country) {
        URI uri = TemperatureUrlUtils.createURI(country);

        return restTemplate.getForObject(uri, TemperatureDTO.class);
    }

}