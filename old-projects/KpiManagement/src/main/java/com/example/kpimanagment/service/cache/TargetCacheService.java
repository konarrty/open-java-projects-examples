package com.example.kpimanagment.service.cache;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Target;
import com.example.kpimanagment.wrapper.TargetCache;

import java.util.List;

public interface TargetCacheService {

    List<TargetCache> getAllTargetsFromCacheForAdding(Long employeeId, Long periodId, Long curatorId);

    List<Long> getAllTargetsFromCacheForRemoving(Long employeeId, Long periodId, Long curatorId);


    TargetCache addTargetToCacheForAdding(Target target, Employee curator);

    void addTargetToCacheForRemoving(Long targetId, Long employeeId, Long periodId, Long curatorId);

    void clearCache(Long employeeId, Long periodId, Long curatorId);
}
