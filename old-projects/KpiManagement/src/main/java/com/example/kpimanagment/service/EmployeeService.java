package com.example.kpimanagment.service;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Period;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    Employee getEmployeeById(Long id);


    Set<Employee> getAllEmployeeForCuratorPrincipal(Principal principal);

    List<Employee> getAllEmployeesWithCuratorRole();

    List<Employee> getAllEmployeesExceptAdminRole();

    Employee createEmployee(Employee employee);

    Employee putEmployee(Employee employee, Long employeeId);

    void deleteEmployee(Long id);


    Employee patchEmployee(Employee employee, Long id);


    BigDecimal getEmployeeParticipationRateForCurator(Employee employee, Employee curator, Period period);
}
