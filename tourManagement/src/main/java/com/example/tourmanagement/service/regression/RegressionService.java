package com.example.tourmanagement.service.regression;

import com.example.tourmanagement.dto.stat.RegressionModelDTO;

public interface RegressionService {
    RegressionModelDTO deduceCorrelationOfWeatherAndTouristTraffic(String name);

}
