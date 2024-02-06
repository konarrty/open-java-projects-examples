package com.example.kpimanagment.service.impl;

import com.example.kpimanagment.exception.NoSuchEntityException;
import com.example.kpimanagment.exception.ServerLogicException;
import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Privilege;
import com.example.kpimanagment.model.PrivilegeRead;
import com.example.kpimanagment.model.PrivilegeWrite;
import com.example.kpimanagment.repository.PrivilegeRepository;
import com.example.kpimanagment.service.PrivilegeService;
import com.example.kpimanagment.service.TargetService;
import com.example.kpimanagment.service.UserService;
import com.example.kpimanagment.service.cache.PrivilegeCacheService;
import com.example.kpimanagment.utils.PeriodUtils;
import com.example.kpimanagment.utils.ReflectionUtils;
import com.example.kpimanagment.wrapper.PrivilegeCache;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    private final PrivilegeCacheService sessionService;
    private final TargetService targetService;
    private final UserService userService;

    private final ReflectionUtils reflectionUtils;
    private final PeriodUtils periodUtils;


    @Override
    public List<Privilege> getAllPrivilegeByEmployeeId(Long employeeId) {

        return privilegeRepository.findAllByEmployeeId(employeeId);
    }

    @Override
    public List<Privilege> getAllPrivilegeByTypeAndEmployeeId(Class<?> type, Long employeeId) {

        return privilegeRepository.findAllByTypeAndEmployeeId(type, employeeId);
    }

    public PrivilegeCache createPrivilegeInCache(Privilege privilege) throws ExecutionException, InterruptedException {
        var principal = SecurityContextHolder.getContext().getAuthentication();
        Employee curator = userService.findByPrincipal(principal).getEmployee();

        return sessionService.addPrivilegeToCacheForAdding(privilege, curator.getId());
    }

    @Override
    public PrivilegeCache patchPrivilegeInCache(Privilege newPrivilege, Long id) throws ExecutionException, InterruptedException {

        var principal = SecurityContextHolder.getContext().getAuthentication();
        Long adminId = userService.findByPrincipal(principal).getEmployee().getId();
        List<PrivilegeCache> privileges = sessionService.getAllPrivilegesFromCacheForAdding(newPrivilege.getEmployee().getId(), adminId);

        PrivilegeCache privilegeCache = privileges.stream().filter(cache ->
                cache.getId().equals(id)).findAny().get();

        privileges.remove(privilegeCache);
        privilegeCache.setPrivilege(newPrivilege);
        privileges.add(privilegeCache);

        return privilegeCache;
    }

    @Override
    public Privilege createPrivilege(Privilege privilege) {

        return privilegeRepository.save(privilege);

    }

    @Override
    public void deletePrivilege(Long id) {
        Privilege privilege = getPrivilegeById(id);

        if (privilege instanceof PrivilegeWrite) {
            targetService.getAllByPeriodAndEmployeeAndCurator(periodUtils.parsePeriod(LocalDate.now().plusMonths(1L)), privilege.getEmployee().getId(), privilege.getUser().getEmployee().getId())
                    .forEach(target -> targetService.deleteTarget(target.getId()));
        }

        privilegeRepository.deleteById(id);
    }

    @Override
    public void deleteAllPrivilegesByEmployeeId(Long employeeId) {

        privilegeRepository.deleteAllByEmployeeId(employeeId);
    }

    @Override
    public void deleteAllPrivilegesByCuratorUserId(Long curatorUserId) {

        privilegeRepository.deleteAllByUserId(curatorUserId);
    }

    @Override
    public void deletePrivilegeFromCache(Long id) {
        Privilege privilege = getPrivilegeById(id);
        if (privilege == null)
            throw new NoSuchEntityException("Право не найдено!");

        Long employeeId = privilege.getEmployee().getId();
        Long curatorId = userService.findByPrincipal(SecurityContextHolder.getContext().getAuthentication()).getEmployee().getId();

        List<PrivilegeCache> privileges = sessionService.getAllPrivilegesFromCacheForAdding(employeeId, curatorId);
        if (privileges != null && !privileges.isEmpty())
            privileges.removeIf(privilegeCache -> Objects.equals(privilegeCache.getPrivilege().getId(), id));

        sessionService.addPrivilegeToCacheForRemoving(id, employeeId, curatorId);

    }

    @Override
    public void deletePrivilegeOnlyFromCache(Long privilegeId, Long employeeId) {
        Long curatorId = userService.findByPrincipal(SecurityContextHolder.getContext().getAuthentication()).getEmployee().getId();

        List<PrivilegeCache> privileges = sessionService.getAllPrivilegesFromCacheForAdding(employeeId, curatorId);

        privileges.removeIf(privilegeCache -> Objects.equals(privilegeCache.getId(), privilegeId));

    }

    @Override
    public boolean isValidPrivilegeSet(Long employeeId) {

        List<PrivilegeWrite> privileges = this.extractPrivilegeWrite(
                getAllPrivilegeByTypeAndEmployeeId(PrivilegeWrite.class, employeeId));

        List<Employee> curaratorsList = new ArrayList<>();
        Set<Employee> curaratorsSet = new HashSet<>();

        for (var privilege : privileges) {
            Employee curator = privilege.getUser().getEmployee();
            curaratorsList.add(curator);
            curaratorsSet.add(curator);
        }
        if (curaratorsList.size() != curaratorsSet.size())
            throw new ServerLogicException("Дублирование куратора недопустимо!");

        return Math.abs(privileges.stream()
                                .mapToDouble(PrivilegeWrite::getParticipationRate)
                                .sum() - 1) < 0.0000001;
    }

    @Override
    public Privilege patchPrivilege(Privilege newPrivilege, Long id) {

        Optional<Privilege> optionalPrivilege = privilegeRepository.findById(id);
        if (optionalPrivilege.isEmpty())
            return privilegeRepository.save(newPrivilege);

        Privilege privilege = optionalPrivilege.get();

        if (!newPrivilege.equals(privilege) && newPrivilege instanceof PrivilegeWrite) {
            targetService.getAllByPeriodAndEmployeeAndCurator(periodUtils.parsePeriod(LocalDate.now().plusMonths(1L)), privilege.getEmployee().getId(), privilege.getUser().getEmployee().getId())
                    .forEach(target -> targetService.deleteTarget(target.getId()));
        }
        privilege = reflectionUtils.merge(privilege, newPrivilege);

        return privilegeRepository.save(privilege);

    }


    @Transactional
    @Override
    public void saveChangesFromCache(Long employeeId) {
        Long curatorId = userService.findByPrincipal(SecurityContextHolder.getContext().getAuthentication()).getEmployee().getId();

        List<PrivilegeCache> privilegesChanges = sessionService.getAllPrivilegesFromCacheForAdding(employeeId, curatorId);
        List<Long> idsToDelete = sessionService.getAllPrivilegesFromCacheForRemoving(employeeId, curatorId);

        if (privilegesChanges != null && !privilegesChanges.isEmpty())
            privilegesChanges.forEach(this::updateIfExistOrSave);

        if (idsToDelete != null && !idsToDelete.isEmpty())
            idsToDelete.forEach(this::deletePrivilege);

        if (!isValidPrivilegeSet(employeeId))
            throw new ServerLogicException("Сумма весов должна быть равна 100%");

        sessionService.clearCache(employeeId, curatorId);

    }


    @Override
    public Privilege getPrivilegeById(Long id) {

        return privilegeRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Сотрудник не найден!"));
    }

    @Transactional
    @Override
    public Privilege updateIfExistOrSave(PrivilegeCache newPrivilege) {

        Privilege privilege = newPrivilege.getPrivilege();

        if (privilege.getId() != null)
            return patchPrivilege(privilege, privilege.getId());
        else {
            return createPrivilege(privilege);
        }
    }

    @Override
    public List<PrivilegeWrite> extractPrivilegeWrite(List<Privilege> privileges) {

        List<PrivilegeWrite> privilegeWrites = new ArrayList<>();

        for (var privilege : privileges) {

            if (privilege instanceof PrivilegeWrite privilegeWrite)
                privilegeWrites.add(privilegeWrite);

        }

        return privilegeWrites;
    }

    @Override
    public List<PrivilegeRead> extractPrivilegeRead(List<Privilege> privileges) {

        List<PrivilegeRead> privilegeReads = new ArrayList<>();

        for (var privilege : privileges) {

            if (privilege instanceof PrivilegeRead privilegeRead)
                privilegeReads.add(privilegeRead);

        }

        return privilegeReads;
    }

}
