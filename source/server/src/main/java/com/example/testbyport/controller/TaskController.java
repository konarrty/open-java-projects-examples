package com.example.testbyport.controller;

import com.example.testbyport.service.TaskService;
import dto.TaskByMonthDTO;
import dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTasksByEmployeeId(@RequestParam Long employeeId) {

        Collection<TaskDTO> tasks = taskService.getAllTasksByEmployeeId(employeeId);
        if (!tasks.isEmpty())
            return ResponseEntity.ok(tasks);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/plans")
    public ResponseEntity<?> getAllTasksByEmployeeIdAndMonth(
            @RequestParam Long employeeId,
            @RequestParam Integer monthNumber) {

        Collection<TaskByMonthDTO> tasks = taskService.getAllTasksByEmployeeIdAndMonth(employeeId, monthNumber);
        if (!tasks.isEmpty())
            return ResponseEntity.ok(tasks);
        else
            return ResponseEntity.notFound().build();
    }
}
