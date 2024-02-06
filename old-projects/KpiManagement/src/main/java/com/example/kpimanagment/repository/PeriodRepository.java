package com.example.kpimanagment.repository;

import com.example.kpimanagment.enums.EPeriodType;
import com.example.kpimanagment.model.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {

}
