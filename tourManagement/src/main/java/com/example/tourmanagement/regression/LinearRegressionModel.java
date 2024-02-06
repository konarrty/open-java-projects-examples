package com.example.tourmanagement.regression;


import com.example.tourmanagement.model.ComputedModel;
import com.example.tourmanagement.utils.MathUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.example.tourmanagement.utils.RangeListUtils.fromRange;

@Component
public class LinearRegressionModel extends RegressionModel {

    public LinearRegressionModel() {
        super();
    }


    public LinearRegressionModel(double[] x, double[] y) {
        super(x, y);
    }

    public LinearRegressionModel(Pair<double[], double[]> pair) {
        super(pair.getFirst(), pair.getSecond());
    }

    @Override
    public ComputedModel compute(Pair<double[], double[]> pair) {

        Assert.notNull(pair.getFirst(), "Массив температур не может быть пустым!");
        Assert.notNull(pair.getSecond(), "Массив спроса не может быть пустым!");

        double b = MathUtils.covariance(xValues, yValues) / MathUtils.variance(xValues);
        double a = MathUtils.mean(yValues) - b * MathUtils.mean(xValues);

        return ComputedModel.createModel(fromRange(-20, 20), -a / b, i -> a + b * i);
    }

}