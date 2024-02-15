package com.example.tourmanagement.utils;

public class MathUtils {

    public static double covariance(double[] x, double[] y) {
        double xmean = mean(x);
        double ymean = mean(y);

        double result = 0;

        for (int i = 0; i < x.length; i++) {
            result += (x[i] - xmean) * (y[i] - ymean);
        }

        result /= x.length - 1;

        return result;
    }

    public static double mean(double[] data) {
        double sum = 0;

        for (double datum : data) {
            sum += datum;
        }

        return sum / data.length;
    }

    public static double variance(double[] data) {
        double mean = mean(data);

        double sumOfSquaredDeviations = 0;

        for (double datum : data) {
            sumOfSquaredDeviations += Math.pow(datum - mean, 2);
        }

        return sumOfSquaredDeviations / (data.length - 1);
    }

}