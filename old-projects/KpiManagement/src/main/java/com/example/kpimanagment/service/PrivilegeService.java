package com.example.kpimanagment.service;

import com.example.kpimanagment.model.Privilege;
import com.example.kpimanagment.model.PrivilegeRead;
import com.example.kpimanagment.model.PrivilegeWrite;
import com.example.kpimanagment.wrapper.PrivilegeCache;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PrivilegeService {


    List<Privilege> getAllPrivilegeByEmployeeId(Long employeeId);


    List<Privilege> getAllPrivilegeByTypeAndEmployeeId(Class<?> type, Long employeeId);


    PrivilegeCache createPrivilegeInCache(Privilege privilege) throws ExecutionException, InterruptedException;

    PrivilegeCache patchPrivilegeInCache(Privilege privilege, Long id) throws ExecutionException, InterruptedException;

    Privilege createPrivilege(Privilege privilege);

    void deletePrivilege(Long id);

    void deleteAllPrivilegesByEmployeeId(Long employeeId);

    void deleteAllPrivilegesByCuratorUserId(Long curatorUserId);

    void deletePrivilegeFromCache(Long id);

    void deletePrivilegeOnlyFromCache(Long privilegeId, Long employeeId);

    boolean isValidPrivilegeSet(Long employeeId);

    Privilege patchPrivilege(Privilege newPrivilege, Long id);

    @Transactional
    void saveChangesFromCache(Long employeeId);

    Privilege getPrivilegeById(Long id);


    Privilege updateIfExistOrSave(PrivilegeCache newPrivilege) throws InterruptedException;

    List<PrivilegeWrite> extractPrivilegeWrite(List<Privilege> privileges);

    List<PrivilegeRead> extractPrivilegeRead(List<Privilege> privileges);

}
