package com.example.testbyport.service;

import dto.EmployeeDTO;

import java.util.Collection;

public interface EmployeeService {
    Collection<EmployeeDTO> getAllEmployees();
}
