package com.example.kpimanagment.repository;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllMarkByTargetEmployeeIdAndTargetPeriodIdAndTargetCurator(Long employeeId, Long periodId, Employee curator);

    List<Mark> findAllMarkByTargetPeriodIdAndTargetEmployeeId(Long periodId, Long employeeId);
}
