package com.example.tourmanagement.specification;

import com.example.tourmanagement.model.entity.Tour;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TourSpecification {

    public static Predicate withNull(@Nullable Root<Tour> root, @Nullable CriteriaQuery<?> query, @Nullable CriteriaBuilder criteriaBuilder) {
        return null;
    }
}

