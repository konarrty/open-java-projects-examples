package com.example.kpimanagment.repository;

import com.example.kpimanagment.model.Indicator;
import com.example.kpimanagment.model.Mark;
import com.example.kpimanagment.model.Target;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Long> {
    boolean existsByName(String name);
    Indicator findByName(String name);
    List<Indicator> deleteByTargets_Empty();
}
