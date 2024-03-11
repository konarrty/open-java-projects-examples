package com.example.tourmanagement.specification;

import com.example.tourmanagement.filtration.Filter;
import com.example.tourmanagement.model.entity.Tour;
import jakarta.persistence.criteria.Path;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {

    public static <T> Specification<T> addSpecification(Filter<T> tourFilter) {

        return (root, query, builder) -> {

            String[] fields = tourFilter.getField().split("\\.");
            Path<Object> attribute = root.get(fields[0]);

            for (int i = 1; i < fields.length; i++) {
                attribute = attribute.get(fields[i]);
            }

            return tourFilter.addPredicate(builder, attribute);
        };
    }
}

