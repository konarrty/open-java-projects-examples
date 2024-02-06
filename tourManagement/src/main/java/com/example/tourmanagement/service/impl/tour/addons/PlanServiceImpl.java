package com.example.tourmanagement.service.impl.tour.addons;

import com.example.tourmanagement.dto.PlanDTO;
import com.example.tourmanagement.mapper.tour.addons.PlanMapper;
import com.example.tourmanagement.model.entity.Plan;
import com.example.tourmanagement.repository.tour.addons.PlanRepository;
import com.example.tourmanagement.service.tour.addons.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    @Override
    public Iterable<PlanDTO> getAllPlans(Long detailsId) {

        Iterable<Plan> plans;
        if (detailsId == 0)
            plans = planRepository.findAll();
        else
            plans = planRepository.findAllByDetailsId(detailsId);

        return planMapper.toDTO(plans);
    }

    @Override
    public PlanDTO createPlan(PlanDTO planDTO) {
        Plan plan = planMapper.toEntity(planDTO);
        planRepository.save(plan);

        return planMapper.toDTO(plan);
    }

    @Override
    public PlanDTO patchPlans(PlanDTO newPlan, Long id) {
        Plan newPlanEntity = planMapper.toEntity(newPlan);

        Plan plan = planRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("План поездки не найден!"));

        planMapper.partialUpdate(plan, newPlanEntity);
        planRepository.save(plan);

        return planMapper.toDTO(plan);
    }

    @Override
    public void deletePlan(Long id) {
        if (!planRepository.existsById(id))
            throw new NoSuchElementException("Тур не найден!");

        planRepository.deleteById(id);
    }

    @Override
    public Plan getPlanById(Long id) {

        return planRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Тур не найден!"));
    }

    @Override
    public PlanDTO findPlanById(Long id) {
        Plan plan = getPlanById(id);

        return planMapper.toDTO(plan);
    }

}
