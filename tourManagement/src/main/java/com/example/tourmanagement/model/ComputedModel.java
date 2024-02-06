package com.example.tourmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputedModel {

    private List<Integer> temperatures = new ArrayList<>();

    private List<Double> demands = new ArrayList<>();

    private double breakEven;

    public static ComputedModel createModel(List<Integer> temperatures, double breakEven, Function<Integer, Double> function) {

        return ComputedModel.builder()
                .temperatures(temperatures)
                .demands(temperatures.parallelStream().map(function).toList())
                .breakEven(breakEven)
                .build();

    }
}
