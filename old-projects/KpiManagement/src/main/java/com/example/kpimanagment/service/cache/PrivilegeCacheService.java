package com.example.kpimanagment.service.cache;

import com.example.kpimanagment.model.Privilege;
import com.example.kpimanagment.wrapper.PrivilegeCache;

import java.util.List;

public interface PrivilegeCacheService {

    List<PrivilegeCache> getAllPrivilegesFromCacheForAdding(Long employeeId, Long curatorId);

    List<Long> getAllPrivilegesFromCacheForRemoving(Long employeeId, Long curatorId);

    PrivilegeCache addPrivilegeToCacheForAdding(Privilege privilege, Long curatorId);

    void addPrivilegeToCacheForRemoving(Long privilegeId, Long employeeId, Long curatorId);

    void clearCache(Long employeeId, Long curatorId);
}
