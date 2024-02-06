package com.example.tourmanagement.specification;

import com.example.tourmanagement.filtration.Filter;
import jakarta.persistence.criteria.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

public class Specifications {

    public static <T> Specification<T> addSpecification(Filter<T> tourFilter) {

        return (root, query, builder) -> {
            String[] fields = tourFilter.getField().split("\\.");
            Path<Object> attribute = root.get(fields[0]);

            for (var field : fields) {
                attribute = attribute.get(field);
            }

            return tourFilter.addPredicate(builder, attribute);
        };
    }
}

