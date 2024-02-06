package com.example.constructionmanagementspring.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {
    public static Type getGenericParameterClass(Type type, int parameterIndex) {

        if (type instanceof ParameterizedType t) {
            return t.getActualTypeArguments()[parameterIndex];
        }
        throw new IllegalStateException();

    }
}