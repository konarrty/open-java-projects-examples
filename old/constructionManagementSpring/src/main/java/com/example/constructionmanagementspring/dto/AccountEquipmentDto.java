package com.example.constructionmanagementspring.dto;

import lombok.Data;

@Data
public class AccountEquipmentDto {
    private Long accountEquipmentId;

    private EmployeeDto employee;
    private EquipmentDto equipment;

}
