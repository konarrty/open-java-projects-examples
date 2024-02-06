package com.example.client.client;

import com.example.client.client.base.RestCollectionOperations;
import dto.MonthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MonthClient {

    private final RestCollectionOperations restTemplate;

    public Collection<MonthDTO> getMonths() {

        return restTemplate.getCollectionForElementClass("http://localhost:8080/api/months",
                MonthDTO.class,
                "Не найдено ни одного месяца");
    }

}
