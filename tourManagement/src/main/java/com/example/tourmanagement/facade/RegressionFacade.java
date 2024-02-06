package com.example.tourmanagement.facade;

import com.example.tourmanagement.api.StatApi;
import com.example.tourmanagement.dto.stat.StatDTO;
import com.example.tourmanagement.model.ComputedModel;
import com.example.tourmanagement.model.entity.Country;
import com.example.tourmanagement.regression.RegressionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class RegressionFacade {

    private final StatApi statApi;
    private final RegressionModel regressionModel;

    public ComputedModel compute(Country country) {

        return regressionModel.compute(fillArrayPair(country));
    }


    public Pair<double[], double[]> fillArrayPair(Country country) {

        double[] list = fillDemandsArray(country);
        double[] doubles = fillTemperatureArray(country);

        return Pair.of(list, doubles);
    }

    private double[] fillDemandsArray(Country country) {

        return statApi
                .getDemandStat(country)
                .getDataSets().get(0)
                .getObservations()
                .values()
                .stream()
                .mapToDouble(d -> d[0])
                .toArray();
    }

    private double calculateAverageTemperature(StatDTO dto) {

        return dto.getDaily().getTemperature_2m_mean()
                .stream()
                .parallel()
                .flatMapToDouble(DoubleStream::of)
                .average()
                .orElse(0);
    }

    private double[] fillTemperatureArray(Country country) {

        return IntStream.range(0, 14).parallel()
                .mapToDouble(i -> calculateAverageTemperatureForOnce(country, i))
                .toArray();
    }

    private double calculateAverageTemperatureForOnce(Country country, int i) {

        return calculateAverageTemperature(statApi.getTemperatureStat(country, i));
    }


}