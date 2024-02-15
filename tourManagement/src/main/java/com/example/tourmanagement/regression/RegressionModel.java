package com.example.tourmanagement.regression;

import com.example.tourmanagement.model.ComputedModel;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

@NoArgsConstructor
public abstract class RegressionModel {
    public abstract ComputedModel compute(Pair<double[], double[]> pair);

}