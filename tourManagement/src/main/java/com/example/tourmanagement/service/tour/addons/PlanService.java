package com.example.tourmanagement.service.tour.addons;

import com.example.tourmanagement.dto.PlanDTO;
import com.example.tourmanagement.model.entity.Plan;

public interface PlanService {
    Iterable<PlanDTO> getAllPlans(Long detailsId);

    PlanDTO createPlan(PlanDTO planDTO);

    PlanDTO patchPlans(PlanDTO newPlan, Long id);

    void deletePlan(Long id);

    Plan getPlanById(Long id);

    PlanDTO findPlanById(Long id);
}
