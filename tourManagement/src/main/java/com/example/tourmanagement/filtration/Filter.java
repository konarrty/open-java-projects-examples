package com.example.tourmanagement.filtration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public interface Filter<T> {

    String getField();

    String getOperation();

    Predicate addPredicate(CriteriaBuilder builder, Path<?> path);
}
