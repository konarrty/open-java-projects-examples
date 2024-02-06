package com.example.constructionmanagementspring.config.annotation;

import com.example.constructionmanagementspring.mapper.DtoMapper;
import com.example.constructionmanagementspring.utils.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class DtoMapperFactory {
    private final Map<Type, DtoMapper<?, ?>> map;

    public DtoMapperFactory(List<DtoMapper<?, ?>> mappers) {

        this.map = mappers.stream().collect(toMap(r -> ReflectionUtils
                        .getGenericParameterClass(r.getClass().getInterfaces()[0].getGenericInterfaces()[0], 0),
                Function.identity()
        ));
    }

    public DtoMapper<?, ?> createMapper(Class<?> type) {

        return map.get(type);
    }
}