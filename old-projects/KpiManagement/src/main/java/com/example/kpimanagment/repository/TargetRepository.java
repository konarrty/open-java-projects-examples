package com.example.kpimanagment.repository;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Indicator;
import com.example.kpimanagment.model.Period;
import com.example.kpimanagment.model.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {

    List<Target> findAllByEmployeeIdAndPeriodId(Long employeeId, Long periodId);

    List<Target> findByEmployeeIdAndCuratorId(@Param(value = "employeeId") Long employeeId, @Param(value = "curatorId") Long curatorId);

    List<Target> findByEmployeeIdAndCuratorIdAndPeriod(@Param(value = "employeeId") Long employeeId, @Param(value = "curatorId") Long curatorId, Period period);

    void deleteAllByCuratorIdAndEmployeeIdAndPeriodId(Long curatorId, Long employeeId, Long periodId);

    void deleteAllByEmployeeId(Long employeeId);

    void deleteAllByCuratorId(Long curatorId);


}
