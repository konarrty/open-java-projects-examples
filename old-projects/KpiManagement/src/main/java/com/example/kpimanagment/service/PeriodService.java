package com.example.kpimanagment.service;

import com.example.kpimanagment.enums.EPeriodType;
import com.example.kpimanagment.model.Period;

import java.util.List;

public interface PeriodService {
    List<Period> getAllPeriod();

    Period getPeriodById(Long id);

    Period createPeriod(Period period);

    Period putPeriod(Period period, Long periodId);

    void deletePeriod(Long id);


    boolean isAfter(Period period);
}
