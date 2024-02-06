package com.example.kpimanagment.service.impl;

import com.example.kpimanagment.dto.KpiReport;
import com.example.kpimanagment.exception.NoSuchEntityException;
import com.example.kpimanagment.exception.ServerLogicException;
import com.example.kpimanagment.model.*;
import com.example.kpimanagment.repository.TargetRepository;
import com.example.kpimanagment.service.IndicatorService;
import com.example.kpimanagment.service.PrivilegeService;
import com.example.kpimanagment.service.TargetService;
import com.example.kpimanagment.service.UserService;
import com.example.kpimanagment.service.cache.TargetCacheService;
import com.example.kpimanagment.utils.KpiReportDtoMapper;
import com.example.kpimanagment.utils.PeriodUtils;
import com.example.kpimanagment.utils.ReflectionUtils;
import com.example.kpimanagment.wrapper.TargetCache;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class TargetServiceImpl implements TargetService {

    private final TargetRepository targetRepository;

    private final TargetCacheService sessionService;
    private final UserService userService;
    private final IndicatorService indicatorService;
    private final PrivilegeService privilegeService;

    private final ReflectionUtils reflectionUtils;
    private final PeriodUtils periodUtils;
    private final KpiReportDtoMapper kpiReportDtoMapper;

    @Override
    public List<Target> getAllTarget() {

        return targetRepository.findAll();
    }

    @Override
    public List<Target> getAllByPeriodAndEmployeeAndCurator(Period period, Long employeeId, Long curatorId) {

        List<Target> targets = targetRepository.findByEmployeeIdAndCuratorId(employeeId, curatorId);
        List<Target> newList = new ArrayList<>();

        targets.stream()
                .filter(target -> periodUtils.isBefore(period, target.getPeriod()))
                .forEach(newList::add);

        return newList;
    }

    @Override
    public List<Target> getAllByPeriodEqualsAndEmployeeAndCurator(Period period, Long employeeId, Long curatorId) {

        return targetRepository.findByEmployeeIdAndCuratorIdAndPeriod(employeeId, curatorId, period);
    }

    @Override
    public Target createTarget(Target target) {

        if (periodUtils.isBeforeLocaleDate(target.getPeriod()))
            throw new ServerLogicException("В данный момент у вас отсутствуют права на добавление задачи для данного сотрудника");

        target.setIndicator(indicatorService.saveIndicatorIfRequired(target.getIndicator()));
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByPrincipal(principal);
        Employee curator = user.getEmployee();
        target.setCurator(curator);

        return targetRepository.save(target);
    }

    @Transactional
    @Override
    public void saveChangesFromCache(Long employeeId, Long periodId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByPrincipal(principal);
        Employee curator = user.getEmployee();
        List<PrivilegeWrite> privileges = privilegeService.extractPrivilegeWrite(user.getPrivileges());

        double initialParticipationRate = 0;
        initialParticipationRate = getAllTargetsByPrincipalAndPeriodAndEmployee(principal, periodId, employeeId).stream()
                .mapToDouble(report -> report.getTarget().getWeight())
                .sum();

        List<TargetCache> targetsChanges = sessionService.getAllTargetsFromCacheForAdding(employeeId, periodId, curator.getId());
        List<Long> idsToDelete = sessionService.getAllTargetsFromCacheForRemoving(employeeId, periodId, curator.getId());

        if (targetsChanges != null && !targetsChanges.isEmpty())
            targetsChanges.forEach(this::updateIfExistOrSave);

        if (idsToDelete != null && !idsToDelete.isEmpty())
            idsToDelete.forEach(this::deleteTarget);

        checkEventualParticipationRate(initialParticipationRate, employeeId, periodId, privileges);

        indicatorService.deleteAllIndicatorByTargets_Empty();
        sessionService.clearCache(employeeId, periodId, curator.getId());
    }

    private void checkEventualParticipationRate(double initialParticipationRate, Long employeeId, Long periodId, List<PrivilegeWrite> privileges) {
        double eventualParticipationRate = 0;
        if (initialParticipationRate != 0) {
            eventualParticipationRate = initialParticipationRate;
        } else if (privileges != null) {
            eventualParticipationRate = privileges.stream()
                    .filter(privilege -> privilege.getEmployee().getId().equals(employeeId))
                    .findFirst()
                    .orElse(new PrivilegeWrite())
                    .getParticipationRate();
        }

        if (!isValidTargetSet(employeeId, periodId, eventualParticipationRate))
            if (eventualParticipationRate != 0) {
                BigDecimal bigDecimalResult = new BigDecimal(eventualParticipationRate * 100);
                bigDecimalResult = bigDecimalResult.setScale(2, RoundingMode.HALF_UP);
                throw new ServerLogicException("Сумма весов должна быть равна " + bigDecimalResult + "%");

            } else
                throw new ServerLogicException("В данный момент вы не обладаете полномочиями на эту операцию");

    }

    @Override
    public void deleteTargetOnlyFromCache(Long targetId, Long employeeId, Long periodId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByPrincipal(principal);
        Employee curator = user.getEmployee();

        List<TargetCache> targets = sessionService.getAllTargetsFromCacheForAdding(employeeId, periodId, curator.getId());

        targets.removeIf(targetCache -> Objects.equals(targetCache.getId(), targetId));

    }

    @Override
    public boolean isValidTargetSet(Long employeeId, Long periodId, double participationRate) {

        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        List<KpiReport> reports = getAllTargetsByPrincipalAndPeriodAndEmployee(principal, periodId, employeeId);
        List<Indicator> indicatorsList = new ArrayList<>();
        Set<Indicator> indicatorsSet = new HashSet<>();

        for (var target : reports) {
            indicatorsList.add(target.getTarget().getIndicator());
            indicatorsSet.add(target.getTarget().getIndicator());
        }

        if (indicatorsList.size() != indicatorsSet.size())
            throw new ServerLogicException("Недопустимо наличие одинаковых задач за один и тот же период");

        return Math.abs(reports.stream()
                                .mapToDouble(report -> report.getTarget().getWeight())
                                .sum() - participationRate) < 0.0000001;
    }

    @Override
    public TargetCache createTargetInCache(Target target) {

        Employee curator = userService.findByPrincipal(SecurityContextHolder.getContext().getAuthentication()).getEmployee();

        return sessionService.addTargetToCacheForAdding(target, curator);
    }

    @Override
    public TargetCache patchTargetInCache(Target newTarget, Long id) {

        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        newTarget.setCurator(userService.findByPrincipal(principal).getEmployee());

        List<TargetCache> targets = sessionService.getAllTargetsFromCacheForAdding(newTarget.getEmployee().getId(), newTarget.getPeriod().getId(), newTarget.getCurator().getId());

        TargetCache targetCache = targets.stream().filter(cache ->
                cache.getId().equals(id)).findFirst().get();

        targets.remove(targetCache);
        targetCache.setReport(kpiReportDtoMapper.mapToDto(newTarget));
        targets.add(targetCache);


        return targetCache;
    }

    @Override
    public Target updateIfExistOrSave(TargetCache newTarget) {

        Target target = newTarget.getReport().getTarget();

        if (target.getId() != null)
            return patchTarget(target, target.getId());
        else {
            return createTarget(target);
        }
    }

    @Override
    public List<KpiReport> getAllTargetsByPrincipalAndPeriodAndEmployee(Principal principal, Long periodId, Long
            employeeId) {

        User user = userService.findByPrincipal(principal);
        return targetRepository.findAll().stream()
//                .peek(::println)
                .filter(target -> target.getCurator().getUser().getId().equals(user.getId()))
                .filter(target -> target.getPeriod().getId().equals(periodId))
                .filter(target -> target.getEmployee().getId().equals(employeeId))
                .map(kpiReportDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<KpiReport> getAllTargetsByPeriodAndEmployee(Long periodId, Long employeeId) {


        return targetRepository.findAllByEmployeeIdAndPeriodId(employeeId, periodId).stream()
                .map(kpiReportDtoMapper::mapToDto)
                .toList();
    }


    @Override
    public Target putTarget(Target newTarget, Long targetId) {
        Target target = getTargetById(targetId);

        newTarget.setId(target.getId());

        return targetRepository.save(newTarget);

    }

    @Override
    public Target patchTarget(Target newTarget, Long targetId) {

        Optional<Target> optionalTarget = targetRepository.findById(targetId);
        newTarget.setIndicator(indicatorService.saveIndicatorIfRequired(newTarget.getIndicator()));
        if (optionalTarget.isEmpty())
            return targetRepository.save(newTarget);

        Target target = optionalTarget.get();

        target = reflectionUtils.merge(target, newTarget);

        return targetRepository.save(target);

    }

    @Override
    public List<KpiReport> findTargetsByEmployeeAndPeriod(Employee employee, Period period) {

        return targetRepository.findAllByEmployeeIdAndPeriodId(employee.getId(), period.getId()).stream()
                .map(kpiReportDtoMapper::mapToDto)
                .toList();

    }

    @Override
    public void deleteTargetByCuratorIdAndEmployeeIdAndPeriodId(Long curatorId, Long employeeId, Long periodId) {

        targetRepository.deleteAllByCuratorIdAndEmployeeIdAndPeriodId(curatorId, employeeId, periodId);

    }

    @Override
    public void deleteTarget(Long id) {

        targetRepository.deleteById(id);
    }

    @Override
    public void deleteAllTargetsByEmployeeId(Long id) {

        targetRepository.deleteAllByEmployeeId(id);

    }

    @Override
    public void deleteAllTargetsByCuratorId(Long id) {

        targetRepository.deleteAllByCuratorId(id);

    }

    @Override
    public void deleteTargetFromCache(Long id) {
        Target target = getTargetById(id);
        if (target == null)
            throw new NoSuchEntityException("Право не найдено!");

        Long employeeId = target.getEmployee().getId();
        Long periodId = target.getPeriod().getId();
        Long curatorId = userService.findByPrincipal(SecurityContextHolder.getContext().getAuthentication()).getEmployee().getId();

        List<TargetCache> privileges = sessionService.getAllTargetsFromCacheForAdding(employeeId, periodId, curatorId);
        if (privileges != null && !privileges.isEmpty())
            privileges.removeIf(privilegeCache -> Objects.equals(privilegeCache.getReport().getTarget().getId(), id));

        sessionService.addTargetToCacheForRemoving(id, employeeId, periodId, curatorId);

    }

    @Override
    public void deleteTargetIfExist(Long id) {
        if (!targetRepository.existsById(id))
            return;

        targetRepository.deleteById(id);
    }

    @Override
    public Target getTargetById(Long id) {

        return targetRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Цель не найдена!"));
    }

    @Override
    public KpiReport getKpiReportByTargetId(Long id) {

        return kpiReportDtoMapper.mapToDto(getTargetById(id));
    }

    @Override
    public double sumRate(Employee curator, Employee employee, Period period) {

        double sum = 0;

        List<Target> targets = getAllByPeriodEqualsAndEmployeeAndCurator(period, employee.getId(), curator.getId());
        for (var target : targets)
            sum += target.getWeight();

        return sum;
    }


}
