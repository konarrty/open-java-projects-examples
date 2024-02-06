package com.example.tourmanagement.filtration.impl;

import com.example.tourmanagement.exception.LogicException;
import com.example.tourmanagement.filtration.TourFilter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Optional;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourFilterBetweenRequest implements TourFilter {

    private String field;
    private Object value;
    private Object value2;
    private String operation;

    @Override
    public Predicate addPredicate(CriteriaBuilder builder, Path<?> path) {

        Method method = ReflectionUtils.findMethod(builder.getClass(), operation, Expression.class, Comparable.class, Comparable.class);

        return (Predicate) Optional.ofNullable(method)
                .map(m -> ReflectionUtils.invokeMethod(method, builder, path,
                        value,
                        value2))
                .orElseThrow(() -> new LogicException("Не существует такой операции!"));

    }


    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TourFilterBetweenRequestHelper {

        private String field;
        private Object value;
        private Object value2;
        private String operation;
    }


    @JsonCreator
    private static TourFilterBetweenRequest create(@NonNull TourFilterBetweenRequestHelper helper) {

        var request = new TourFilterBetweenRequest();
        request.setField(helper.getField());
        request.setOperation(helper.getOperation());
        request.setValue(request.getField().equals("startDateTime") ? LocalDateTime.parse(helper.getValue().toString()) : helper.getValue());
        request.setValue2(request.getField().equals("startDateTime") ? LocalDateTime.parse(helper.getValue2().toString()) : helper.getValue2());

        return request;
    }


}
