package com.example.constructionmanagementspring.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;

    private PassportDto passport;

    private UserDto user;
}
