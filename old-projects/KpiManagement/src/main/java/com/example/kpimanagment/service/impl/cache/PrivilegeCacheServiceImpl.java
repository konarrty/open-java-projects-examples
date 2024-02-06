package com.example.kpimanagment.service.impl.cache;

import com.example.kpimanagment.model.Privilege;
import com.example.kpimanagment.service.base.AbstractCacheService;
import com.example.kpimanagment.service.cache.PrivilegeCacheService;
import com.example.kpimanagment.wrapper.PrivilegeCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unchecked")
@Service
public class PrivilegeCacheServiceImpl extends AbstractCacheService implements PrivilegeCacheService {

    public PrivilegeCacheServiceImpl(@Qualifier("privileges") Cache cache) {
        super(cache);
    }

    @Override
    public List<PrivilegeCache> getAllPrivilegesFromCacheForAdding(Long employeeId, Long curatorId) {

        return (List<PrivilegeCache>) cacheGet("privilegesToAdd" + employeeId + "_" + curatorId);
    }

    @Override
    public List<Long> getAllPrivilegesFromCacheForRemoving(Long employeeId, Long curatorId) {

        return (List<Long>) cacheGet("privilegesToRemove" + employeeId + "_" + curatorId);
    }

    @Override
    public PrivilegeCache addPrivilegeToCacheForAdding(Privilege privilege, Long curatorId) {
        String attributeName = "privilegesToAdd" + privilege.getEmployee().getId() + "_" + curatorId;
        List<PrivilegeCache> privileges;

        synchronized (this) {
            privileges = (List<PrivilegeCache>) cacheGet(attributeName);

            if (privileges == null) {
                privileges = Collections.synchronizedList(new ArrayList<>());
                cache.put(attributeName, privileges);
                cache.put("privilegesId", new AtomicLong(0L));
            }
        }
        AtomicLong id = (AtomicLong) cacheGet("privilegesId");
        PrivilegeCache privilegeCache = new PrivilegeCache(id.getAndIncrement(), privilege);
        privileges.add(privilegeCache);

        return privilegeCache;
    }

    @Override
    public void addPrivilegeToCacheForRemoving(Long privilegeId, Long employeeId, Long curatorId) {
        String attributeName = "privilegesToRemove" + employeeId + "_" + curatorId;
        List<Long> privileges = (List<Long>) cacheGet(attributeName);

        if (privileges == null) {
            privileges = new ArrayList<>();
            cache.put(attributeName, privileges);

        }
        privileges.add(privilegeId);

    }

    @Override
    public void clearCache(Long employeeId, Long curatorId) {
        String attributeAddingName = "privilegesToAdd" + employeeId + "_" + curatorId;
        String attributeRemovingName = "privilegesToRemove" + employeeId + "_" + curatorId;

        List<PrivilegeCache> privilegesAdding = (List<PrivilegeCache>) cacheGet(attributeAddingName);
        List<Long> privilegesRemoving = (List<Long>) cacheGet(attributeRemovingName);

        if (privilegesAdding != null && !privilegesAdding.isEmpty()) {
            privilegesAdding.clear();
            cache.put("privilegesId", new AtomicLong(0L));

        }
        if (privilegesRemoving != null && !privilegesRemoving.isEmpty())
            privilegesRemoving.clear();
    }


}
