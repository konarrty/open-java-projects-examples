package com.example.tourmanagement.dto.stat;

import com.example.tourmanagement.model.ComputedModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegressionModelDTO {

    private List<Integer> temperatures = new ArrayList<>();

    private List<Double> demands = new ArrayList<>();

    private double breakEven;

    public static RegressionModelDTO createDTO(ComputedModel computedModel) {

        return RegressionModelDTO.builder()
                .temperatures(computedModel.getTemperatures())
                .demands(computedModel.getDemands())
                .breakEven(computedModel.getBreakEven())
                .build();

    }
}