package com.example.tourmanagement.filtration.impl;

import com.example.tourmanagement.filtration.TourFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourFilterEqualRequest implements TourFilter {

    private String field;
    private List<Object> value = new ArrayList<>();
    private String operation;

    @Override
    public Predicate addPredicate(CriteriaBuilder builder, Path<?> path) {

        return value.size() > 1 ? path.in(value) : builder.equal(path, value.get(0));
    }

}
