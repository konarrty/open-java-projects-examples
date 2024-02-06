package com.example.kpimanagment.service;

import com.example.kpimanagment.dto.KpiReport;
import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Period;
import com.example.kpimanagment.model.Target;
import com.example.kpimanagment.wrapper.TargetCache;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

public interface TargetService {
    List<Target> getAllTarget();

    List<KpiReport> findTargetsByEmployeeAndPeriod(Employee employee, Period period);

    void deleteTargetByCuratorIdAndEmployeeIdAndPeriodId(Long curatorId, Long employeeId, Long periodId);

    void deleteTarget(Long id);

    void deleteAllTargetsByEmployeeId(Long id);

    void deleteAllTargetsByCuratorId(Long id);

    void deleteTargetFromCache(Long id);

    void deleteTargetIfExist(Long id);


    Target getTargetById(Long id);

    List<Target> getAllByPeriodAndEmployeeAndCurator(Period period, Long employeeId, Long curatorId);

    List<Target> getAllByPeriodEqualsAndEmployeeAndCurator(Period period, Long employeeId, Long curatorId);

    Target createTarget(Target target);

    @Transactional
    void saveChangesFromCache(Long employeeId, Long periodId);

    void deleteTargetOnlyFromCache(Long targetId, Long employeeId, Long periodId);


    boolean isValidTargetSet(Long employeeId, Long periodId, double participationRate);

    TargetCache createTargetInCache(Target target);

    TargetCache patchTargetInCache(Target newTarget, Long id);

    Target updateIfExistOrSave(TargetCache newTarget);

    List<KpiReport> getAllTargetsByPrincipalAndPeriodAndEmployee(Principal principal, Long periodId, Long employeeId);

//    List<KpiReport> getAllTargetsByPrincipalAndPeriodAndEmployee(Long periodId, Long employeeId);

    List<KpiReport> getAllTargetsByPeriodAndEmployee(Long periodId, Long employeeId);

    Target putTarget(Target target, Long targetId);
    Target patchTarget(Target target, Long targetId);


    KpiReport getKpiReportByTargetId(Long id);

    double sumRate(Employee curator, Employee employee, Period period);
}
