package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.EmployeeDto;
import com.example.constructionmanagementspring.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends DtoMapper<Employee, EmployeeDto> {

}