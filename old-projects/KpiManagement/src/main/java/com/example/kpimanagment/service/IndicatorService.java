package com.example.kpimanagment.service;

import com.example.kpimanagment.model.Indicator;

import java.util.List;

public interface IndicatorService {
    List<Indicator> getAllIndicator();

    void deleteAllIndicatorByTargets_Empty();

    Indicator getIndicatorById(Long id);

    Indicator createIndicator(Indicator indicator);

    void deleteIndicatorIfNoTarget(Indicator indicator);

    Indicator saveIndicatorIfRequired(Indicator indicator);

    Indicator putIndicator(Indicator indicator, Long indicatorId);

    void deleteIndicator(Long id);

    Indicator getIndicatorByName(String name);
}
