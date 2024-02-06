package com.example.client.client;

import com.example.client.client.base.RestCollectionOperations;
import dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmployeeClient {

    private final RestCollectionOperations restTemplate;

    public Collection<EmployeeDTO> getEmployees() {

        return restTemplate.getCollectionForElementClass("http://localhost:8080/api/employees",
                EmployeeDTO.class,
                "Не найдено ни одного сотрудника");
    }
}
