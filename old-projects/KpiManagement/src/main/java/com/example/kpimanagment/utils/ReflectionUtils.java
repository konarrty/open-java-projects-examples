package com.example.kpimanagment.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
@Getter
@RequiredArgsConstructor
public class ReflectionUtils {

    public <T> T merge(T obj, T newObj) {

        Field[] fields = getAllFields(obj.getClass());
        try {
            for (var field : fields) {

                field.setAccessible(true);

                Object value = field.get(newObj);

                if (value != null) {
                    field.set(obj, value);
                }

            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return obj;

    }

    public static Field[] getAllFields(Class<?> clazz) {
        Field[] fields = {};
        while (clazz != null) {
            fields = ArrayUtils.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}

