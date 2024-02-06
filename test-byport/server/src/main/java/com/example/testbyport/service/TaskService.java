package com.example.testbyport.service;

import dto.TaskByMonthDTO;
import dto.TaskDTO;

import java.util.Collection;

public interface TaskService {
    Collection<TaskDTO> getAllTasksByEmployeeId(Long employeeId);

    Collection<TaskByMonthDTO> getAllTasksByEmployeeIdAndMonth(Long employeeId, Integer monthNumber);
}
