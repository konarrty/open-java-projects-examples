package com.example.kpimanagment.service.base;


import com.example.kpimanagment.service.CacheService;
import org.springframework.cache.Cache;

public abstract class AbstractCacheService implements CacheService {

    protected final Cache cache;

    protected AbstractCacheService(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Object cacheGet(String name) {

        var cacheObject = cache.get(name);
        if (cacheObject == null)
            return null;
        else
            return cacheObject.get();
    }
}
