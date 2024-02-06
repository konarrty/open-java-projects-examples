package com.example.tourmanagement.repository.tour.addons;

import com.example.tourmanagement.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Iterable<Plan> findAllByDetailsId(Long detailsId);
}
