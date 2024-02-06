package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.UnitDto;
import com.example.constructionmanagementspring.model.Unit;
import com.example.constructionmanagementspring.service.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','PRIVIDER')")
@RestController
@RequestMapping("api/units")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitsService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllUnits() {

        List<Unit> unitsList = unitsService.getAllUnits();

        if (!unitsList.isEmpty())
            return ResponseEntity.ok(unitsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/", ""})
    public Unit createUnits(@BodyToEntity(UnitDto.class) Unit unit) {

        return unitsService.createUnit(unit);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Unit putUnits(@Valid @RequestBody Unit units, @PathVariable Long id) {

        return unitsService.putUnit(units, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteUnits(@PathVariable Long id) {

        unitsService.deleteUnit(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Unit getUnitsById(@PathVariable Long id) {

        return unitsService.getUnitById(id);
    }


}
