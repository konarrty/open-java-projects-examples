package com.example.kpimanagment.utils;

import com.example.kpimanagment.model.Employee;
import org.springframework.stereotype.Component;

@Component(value = "employeeFilterUtils")
public class EmployeeFilterUtils {

    public boolean isNotMe(Employee employee, Employee me) {

        return employee.equals(me);

    }

}

