package com.example.kpimanagment.dto;

import com.example.kpimanagment.model.Target;
import com.example.kpimanagment.model.User;
import lombok.*;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiReport {

    private Target target;

    private double executionIndex;

    private double weightFactor;

    public KpiReport(Target target) {
        this.target = target;
    }
}
