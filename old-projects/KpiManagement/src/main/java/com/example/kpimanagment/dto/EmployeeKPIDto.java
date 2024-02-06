package com.example.kpimanagment.dto;

import com.example.kpimanagment.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeKPIDto {
    private Long id;

    private String fullName;

    private String position;

    private double kpi;

    private User user;

}
