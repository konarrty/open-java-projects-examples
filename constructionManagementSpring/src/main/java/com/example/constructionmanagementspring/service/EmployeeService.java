package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees(int page);

    Employee getEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    Employee putEmployee(Employee employee, Long employeeId);

    void deleteEmployee(Long id);

}
