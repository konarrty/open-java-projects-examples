package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.Unit;

import java.util.List;

public interface UnitService {
    List<Unit> getAllUnits();

    Unit getUnitById(Long id);

    Unit createUnit(Unit units);

    Unit putUnit(Unit units, Long unitsId);

    void deleteUnit(Long id);

}
