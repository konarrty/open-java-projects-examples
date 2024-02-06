package com.example.tourmanagement.controller.tour.addons;

import com.example.tourmanagement.dto.PlanDTO;
import com.example.tourmanagement.service.tour.addons.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService plansService;


    @GetMapping
    public ResponseEntity<?> getAllPlans(@RequestParam(required = false, defaultValue = "0") Long detailsId) {

        Iterable<PlanDTO> plansList = plansService.getAllPlans(detailsId);
        if (plansList.iterator().hasNext())
            return ResponseEntity.ok(plansList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PlanDTO createPlans(@RequestBody PlanDTO planDTO) {

        return plansService.createPlan(planDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePlans(@PathVariable Long id) {

        plansService.deletePlan(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public PlanDTO patchPlans(@RequestBody PlanDTO planDTO, @PathVariable Long id) {

        return plansService.patchPlans(planDTO, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PlanDTO getPlanById(@PathVariable Long id) {

        return plansService.findPlanById(id);
    }


}
