package com.example.kpimanagment.service.impl.cache;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Target;
import com.example.kpimanagment.service.base.AbstractCacheService;
import com.example.kpimanagment.service.cache.TargetCacheService;
import com.example.kpimanagment.utils.KpiReportDtoMapper;
import com.example.kpimanagment.wrapper.TargetCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unchecked")
@Service
public class TargetCacheServiceImpl extends AbstractCacheService implements TargetCacheService {
    private final KpiReportDtoMapper kpiReportDtoMapper;

    public TargetCacheServiceImpl(@Qualifier("targets") Cache cache, KpiReportDtoMapper kpiReportDtoMapper) {
        super(cache);
        this.kpiReportDtoMapper = kpiReportDtoMapper;
    }

    @Override
    public List<TargetCache> getAllTargetsFromCacheForAdding(Long employeeId, Long periodId, Long curatorId) {

        return (List<TargetCache>) cacheGet("targetsToAdd" + employeeId + "_" + periodId + "_" + curatorId);
    }

    @Override
    public List<Long> getAllTargetsFromCacheForRemoving(Long employeeId, Long periodId, Long curatorId) {

        return (List<Long>) cacheGet("targetsToRemove" + employeeId + "_" + periodId + "_" + curatorId);
    }

    @Override
    public TargetCache addTargetToCacheForAdding(Target target, Employee curator) {
        String attributeName = "targetsToAdd" + target.getEmployee().getId() + "_" + target.getPeriod().getId() + "_" + curator.getId();
        List<TargetCache> targets;

        synchronized (this) {
            targets = (List<TargetCache>) cacheGet(attributeName);

            if (targets == null) {
                targets = Collections.synchronizedList(new ArrayList<>());
                cache.put(attributeName, targets);
                cache.put("targetsId", new AtomicLong(0L));

            }
        }
        AtomicLong id = (AtomicLong) cacheGet("targetsId");
        TargetCache targetCache = new TargetCache(id.getAndIncrement(), kpiReportDtoMapper.mapToDto(target));
        targets.add(targetCache);


        return targetCache;
    }

    @Override
    public void addTargetToCacheForRemoving(Long targetId, Long employeeId, Long periodId, Long curatorId) {
        String attributeName = "targetsToRemove" + employeeId + "_" + periodId + "_" + curatorId;
        List<Long> targets = (List<Long>) cacheGet(attributeName);

        if (targets == null) {
            targets = new ArrayList<>();
            cache.put(attributeName, targets);

        }
        targets.add(targetId);

    }

    @Override
    public void clearCache(Long employeeId, Long periodId, Long curatorId) {
        String attributeAddingName = "targetsToAdd" + employeeId + "_" + periodId + "_" + curatorId;
        String attributeRemovingName = "targetsToRemove" + employeeId + "_" + periodId + "_" + curatorId;

        List<TargetCache> targetsAdding = (List<TargetCache>) cacheGet(attributeAddingName);
        List<Long> targetsRemoving = (List<Long>) cacheGet(attributeRemovingName);

        if (targetsAdding != null && !targetsAdding.isEmpty()) {
            targetsAdding.clear();
            ((AtomicLong) cacheGet("targetsId")).set(0L);

        }
        if (targetsRemoving != null && !targetsRemoving.isEmpty())
            targetsRemoving.clear();
    }


}
