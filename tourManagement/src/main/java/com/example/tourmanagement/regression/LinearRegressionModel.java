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


    @Override
    public ComputedModel compute(Pair<double[], double[]> pair) {

        Assert.notNull(pair.getFirst(), "Массив температур не может быть пустым!");
        Assert.notNull(pair.getSecond(), "Массив спроса не может быть пустым!");

        double b = MathUtils.covariance(pair.getFirst(), pair.getSecond()) / MathUtils.variance(pair.getFirst());
        double a = MathUtils.mean(pair.getSecond()) - b * MathUtils.mean(pair.getFirst());

        return ComputedModel.createModel(fromRange(-20, 20), -a / b, i -> a + b * i);
    }

}