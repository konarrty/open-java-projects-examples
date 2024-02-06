package com.example.kpimanagment.generator;

import java.util.concurrent.atomic.AtomicLong;

public class LongGenerator {
    private static final AtomicLong counter = new AtomicLong(0);

    public static Long generateId() {
        return counter.incrementAndGet();
    }
}