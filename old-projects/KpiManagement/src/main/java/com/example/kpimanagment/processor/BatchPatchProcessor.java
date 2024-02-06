package com.example.kpimanagment.processor;

import com.example.kpimanagment.annotation.BatchConverter;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class BatchPatchProcessor implements HandlerMethodArgumentResolver {

    private final ConversionService conversionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BatchConverter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String parameterType = null;

        if (parameter.getGenericParameterType() instanceof ParameterizedType p) {
            parameterType = p.getActualTypeArguments()[0].getTypeName();
        }
        Class<?> clazz = Class.forName(parameterType);

        Map<String, String[]> parameterMap = new HashMap<>(webRequest.getParameterMap());
        parameterMap.remove("_method");
        Field[] fields = clazz.getDeclaredFields();

        String[] keys = parameterMap.get(parameter.getParameterName());

        parameterMap.remove(parameter.getParameterName());
        parameterMap.put("id", keys);
        var privileges = new ArrayList<>();

        String[] names = webRequest.getParameterValues(Objects.requireNonNull(parameter.getParameterName()));

        if (names == null)
            return privileges;

        int listSize = names.length;

        for (int i = 0; i < listSize; i++) {

            var privilege = clazz.getDeclaredConstructor().newInstance();

            for (var entry : parameterMap.entrySet()) {

                var value = Arrays.stream(entry.getValue()).skip(i).findFirst().get();

                for (var field : fields) {

                    field.setAccessible(true);

                    if (!field.getName().equals(entry.getKey()))
                        continue;

                    field.set(privilege, conversionService.convert(value, field.getType()));
                }
            }
            privileges.add(privilege);

        }
        return privileges;

    }
}
