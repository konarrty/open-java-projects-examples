package com.example.astontaskspringrestful.util;

import org.springframework.lang.NonNull;

import java.util.Collection;

public class CollectionUtils {
    public static <T> boolean isNotEmpty(@NonNull Collection<T> collection) {

        return !collection.isEmpty();
    }
}
