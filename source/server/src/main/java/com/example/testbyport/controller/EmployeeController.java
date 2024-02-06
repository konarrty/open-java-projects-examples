package com.example.testbyport.controller;

import com.example.testbyport.service.EmployeeService;
import dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {


        Collection<EmployeeDTO> employees = employeeService.getAllEmployees();
        if (!employees.isEmpty())
            return ResponseEntity.ok(employees);
        else
            return ResponseEntity.notFound().build();
    }
}
