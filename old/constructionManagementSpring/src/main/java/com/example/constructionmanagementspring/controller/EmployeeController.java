package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.EmployeeDto;
import com.example.constructionmanagementspring.model.Employee;
import com.example.constructionmanagementspring.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyRole('ADMIN')")
@RestController
@RequestMapping("api/employees")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeesService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllEmployees(@RequestParam(value = "page",required = false, defaultValue = "0") int page) {

        List<Employee> employeesList = employeesService.getAllEmployees(page);

        if (!employeesList.isEmpty())
            return ResponseEntity.ok(employeesList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/", ""})
    public Employee createEmployees(@Valid @BodyToEntity(EmployeeDto.class) Employee employee) {

        return employeesService.createEmployee(employee);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Employee putEmployees(@Valid @BodyToEntity(EmployeeDto.class) Employee employee, @PathVariable Long id) {

        return employeesService.putEmployee(employee, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteEmployees(@PathVariable Long id) {

        employeesService.deleteEmployee(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Employee getEmployeesById(@PathVariable Long id) {

        return employeesService.getEmployeeById(id);
    }


}
