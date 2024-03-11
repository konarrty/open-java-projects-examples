package com.example.aston.api;

import com.example.aston.config.SimpleHttpClient;
import com.example.aston.config.SimpleObjectMapper;
import com.example.aston.dto.TemperatureDTO;
import com.example.aston.enums.Country;
import com.example.aston.utils.TemperatureUrlUtils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TemperatureApi {
    private final SimpleHttpClient client;
    private final SimpleObjectMapper mapper;

    public TemperatureApi(SimpleHttpClient client, SimpleObjectMapper mapper) {

        this.client = client;
        this.mapper = mapper;
    }

    public TemperatureDTO getTemperatureByCountry(Country country) {
        URI uri = TemperatureUrlUtils.createURI(country);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = client.sendAndReceive(request);

        return mapper.readAndConvert(response.body(), TemperatureDTO.class);
    }

}