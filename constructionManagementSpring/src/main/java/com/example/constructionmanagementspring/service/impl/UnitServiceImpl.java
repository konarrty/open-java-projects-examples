package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.Unit;
import com.example.constructionmanagementspring.repository.UnitRepository;
import com.example.constructionmanagementspring.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public List<Unit> getAllUnits() {

        return unitRepository.findAll();
    }

    @Override
    public Unit createUnit(Unit unit) {

        return unitRepository.save(unit);
    }

    @Override
    public Unit putUnit(Unit newUnit, Long unitId) {

        Unit unit = unitRepository
                .findById(unitId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Номенклатурная группа  не найден!"));

        newUnit.setId(unit.getId());

        return unitRepository.save(newUnit);
    }

    @Override
    public void deleteUnit(Long id) {
        if (!unitRepository.existsById(id))
            throw new NoSuchEntityException("Номенклатурная группа  не найден!");

        unitRepository.deleteById(id);
    }

    @Override
    public Unit getUnitById(Long id) {

        return unitRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException("Номенклатурная группа не найден!"));
    }
}
