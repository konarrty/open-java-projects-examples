package com.example.tourmanagement.api;

import com.example.tourmanagement.dto.stat.DemandStatDTO;
import com.example.tourmanagement.dto.stat.StatDTO;
import com.example.tourmanagement.exception.LogicException;
import com.example.tourmanagement.model.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatApi {

    private final RestTemplate restTemplate;

    public StatDTO getTemperatureStat(Country country, int i) {

        String url = "https://archive-api.open-meteo.com/v1/archive?latitude=" + country.getLatitude() + "&longitude=" + country.getLongitude() + "&start_date=" + (2022 - i) + "-01-01&end_date=" + (2023 - i) + "-01-01&daily=temperature_2m_mean&timezone=Europe%2FLondon";
        var dto = restTemplate
                .getForEntity(
                        URI.create(url),
                        StatDTO.class)
                .getBody();

        return Optional.ofNullable(dto)
                .orElseThrow(() -> new LogicException("Данных по этой стране не обнаружено"));


    }

    public DemandStatDTO getDemandStat(Country country) {

        String url = "https://stats.oecd.org/SDMX-JSON/data/TOURISM_INBOUND/" + country.getAbbreviation() +
                     ".INB_ARRIVALS_TOTAL.DEMAND/all?startTime=2008&endTime=2021&dimensionAtObservation=allDimensions";

        DemandStatDTO dto = restTemplate
                .getForEntity(
                        URI.create(url),
                        DemandStatDTO.class)
                .getBody();

        return Optional.ofNullable(dto)
                .orElseThrow(() -> new LogicException("Данных по этой стране не обнаружено"));
    }
}
