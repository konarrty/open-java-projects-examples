package com.example.testbyport.service.impl;

import com.example.testbyport.mapper.TaskMapper;
import com.example.testbyport.repostory.TaskRepository;
import com.example.testbyport.service.TaskService;
import dto.TaskByMonthDTO;
import dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.example.testbyport.utils.TaskWithRangeFormatterUtils.formatTaskWithRangeForLogging;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Collection<TaskDTO> getAllTasksByEmployeeId(Long employeeId) {

        var tasks = taskRepository.findAllByEmployeeId(employeeId);

        return taskMapper.toDto(tasks);
    }

    @Override
    public Collection<TaskByMonthDTO> getAllTasksByEmployeeIdAndMonth(Long employeeId, Integer monthNumber) {
        var tasks = taskRepository.findAllByEmployeeId(employeeId);

        log.info("Выполняется построение плана выполнения следующих работ(в " + monthNumber + " месяце): " + tasks);

        var ranges = tasks.stream()
                .map(t -> t.extractRangeByMonthNumber(monthNumber))
                .toList();

        log.info("Определён план выполнения работ(в " + monthNumber + " месяце): " + formatTaskWithRangeForLogging(tasks, ranges));

        return taskMapper.toByMonthWithRange(tasks, ranges);
    }
}
