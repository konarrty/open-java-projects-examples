package com.example.kpimanagment.service.impl;

import com.example.kpimanagment.exception.NoSuchEntityException;
import com.example.kpimanagment.model.Indicator;
import com.example.kpimanagment.repository.IndicatorRepository;
import com.example.kpimanagment.service.IndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndicatorServiceImpl implements IndicatorService {

    private final IndicatorRepository indicatorRepository;

    @Override
    public List<Indicator> getAllIndicator() {

        return indicatorRepository.findAll(Sort.by("id"));
    }

    @Override
    public Indicator createIndicator(Indicator indicator) {

        return indicatorRepository.save(indicator);
    }

    @Override
    public void deleteIndicatorIfNoTarget(Indicator indicator) {

        Indicator refreshIndicator = getIndicatorById(indicator.getId());
        if (refreshIndicator.getTargets().size() < 2) {
            deleteIndicator(refreshIndicator.getId());
        }
    }

    @Override
    public Indicator saveIndicatorIfRequired(Indicator indicator) {
        if (indicatorRepository.existsByName(indicator.getName()))
            return indicatorRepository.findByName(indicator.getName());

        return indicatorRepository.save(indicator);
    }

    @Override
    public Indicator putIndicator(Indicator newIndicator, Long indicatorId) {
        Indicator indicator = indicatorRepository.findById(indicatorId).orElseThrow(() ->
                new NoSuchEntityException("Показатель не найден!"));

        newIndicator.setId(indicator.getId());

        return indicatorRepository.save(newIndicator);

    }

    @Override
    public void deleteIndicator(Long id) {
        if (!indicatorRepository.existsById(id))
            throw new NoSuchEntityException("Показатель не найден!");

        indicatorRepository.deleteById(id);
    }

    @Override
    public void deleteAllIndicatorByTargets_Empty() {

        indicatorRepository.deleteByTargets_Empty();
    }

    @Override
    public Indicator getIndicatorById(Long id) {

        return indicatorRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Показатель не найден!"));
    }

    @Override
    public Indicator getIndicatorByName(String name) {

        return indicatorRepository.findByName(name);
    }

}
