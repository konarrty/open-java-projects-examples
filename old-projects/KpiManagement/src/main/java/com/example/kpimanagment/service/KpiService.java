package com.example.kpimanagment.service;

import com.example.kpimanagment.dto.EmployeeKPIDto;
import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Mark;

import java.util.List;

public interface KpiService {

    double calculateKpi(EmployeeKPIDto employee);

    double calculateKpi(Employee employee);
}
