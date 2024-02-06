package com.example.testbyport.service.impl;

import com.example.testbyport.mapper.EmployeeMapper;
import com.example.testbyport.repostory.EmployeeRepository;
import com.example.testbyport.service.EmployeeService;
import dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public Collection<EmployeeDTO> getAllEmployees() {

        var employees = employeeRepository.findAll();

        return employeeMapper.toDto(employees);
    }
}
