package com.example.tourmanagement.filtration.impl;

import com.example.tourmanagement.filtration.TourFilter;
import com.example.tourmanagement.exception.LogicException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Optional;


//@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourFilterRequest implements TourFilter {

    private String field;
    private String value;
    private String operation;

    @Override
    public String getOperation() {
        return operation;
    }

    @Override
    public Predicate addPredicate(CriteriaBuilder builder, Path<?> path) {

        Method method = ReflectionUtils.findMethod(builder.getClass(), operation, Expression.class, Comparable.class);

        return (Predicate) Optional.ofNullable(method)
                .map(m -> ReflectionUtils.invokeMethod(method, path, value))
                .orElseThrow(() -> new LogicException("Не существует такой операции!"));
    }

}
