package com.example.testbyport.mapper;

import com.example.testbyport.entity.Employee;
import dto.EmployeeDTO;
import org.mapstruct.*;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO toDto(Employee employee);

    Collection<EmployeeDTO> toDto(Iterable<Employee> employee);
}