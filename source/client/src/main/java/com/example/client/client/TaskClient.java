package com.example.client.client;

import com.example.client.client.base.RestCollectionOperations;
import dto.TaskByMonthDTO;
import dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaskClient {

    private final RestCollectionOperations restTemplate;

    public Collection<TaskDTO> getTasksByEmployeeId(Long employeeId) {

        return restTemplate.getCollectionForElementClass("http://localhost:8080/api/tasks?employeeId=" + employeeId,
                TaskDTO.class,
                "Не найдено ни одной задачи");
    }

    public Collection<TaskByMonthDTO> getTasksByEmployeeIdAndMonthNumber(Long employeeId, Integer number) {

        return restTemplate.getCollectionForElementClass("http://localhost:8080/api/tasks/plans?employeeId=" + employeeId + "&monthNumber=" + number,
                TaskByMonthDTO.class,
                "Не найдено ни одной задачи");
    }
}
