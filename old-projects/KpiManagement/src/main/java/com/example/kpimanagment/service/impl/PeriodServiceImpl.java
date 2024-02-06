package com.example.kpimanagment.service.impl;

import com.example.kpimanagment.exception.NoSuchEntityException;
import com.example.kpimanagment.model.Period;
import com.example.kpimanagment.repository.PeriodRepository;
import com.example.kpimanagment.service.PeriodService;
import com.example.kpimanagment.utils.PeriodUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepository;
    private final PeriodUtils periodUtils;

    @Override
    public List<Period> getAllPeriod() {

        return periodRepository.findAll();
    }

    @Override
    public Period createPeriod(Period period) {

        return periodRepository.save(period);
    }

    @Override
    public Period putPeriod(Period newPeriod, Long periodId) {
        Period period = getPeriodById(periodId);

        newPeriod.setId(period.getId());

        return periodRepository.save(newPeriod);

    }

    @Override
    public void deletePeriod(Long id) {

        periodRepository.deleteById(id);
    }

    @Override
    public Period getPeriodById(Long id) {

        return periodRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Период не найден!"));
    }

    @Override
    public boolean isAfter(Period period) {

        return !periodUtils.isBeforeLocaleDate(period);
    }


}
