package com.example.astontaskspringrestful.util;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.TimeUnit;

public class ResponseEntityUtils {
    public static <T> ResponseEntity<T> cacheDefault(T model, long cacheTime) {

        return ResponseEntity.ok()
                .cacheControl(
                        CacheControl.maxAge(cacheTime, TimeUnit.SECONDS)
                                .cachePublic())
                .body(model);
    }

    public static <T> ResponseEntity<T> noCache(T model) {

        return ResponseEntity.ok()
                .cacheControl(
                        CacheControl.noCache())
                .body(model);
    }
}
