package com.example.tourmanagement.regression;

import com.example.tourmanagement.model.ComputedModel;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

@NoArgsConstructor
public abstract class RegressionModel {

    protected double[] xValues;

    protected double[] yValues;

    public RegressionModel(double[] x, double[] y) {
        this.xValues = x;
        this.yValues = y;
    }

    public double[] getXValues() {
        return this.xValues;
    }


    public double[] getYValues() {
        return this.yValues;
    }


    public abstract ComputedModel compute(Pair<double[], double[]> pair);

}