package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.EquipmentDto;
import com.example.constructionmanagementspring.model.Equipment;
import com.example.constructionmanagementspring.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/equipments")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentsService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllEquipments(@RequestParam int page) {

        List<Equipment> equipmentsList = equipmentsService.getAllEquipments(page);

        if (!equipmentsList.isEmpty())
            return ResponseEntity.ok(equipmentsList);
        else
            return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/", ""})
    public Equipment createEquipments(@Valid @BodyToEntity(EquipmentDto.class) Equipment equipment) {

        return equipmentsService.createEquipment(equipment);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{equipmentId}")
    public Equipment putEquipments(@Valid @BodyToEntity(EquipmentDto.class) Equipment equipment, @PathVariable Long equipmentId) {

        return equipmentsService.putEquipment(equipment, equipmentId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{equipmentId}")
    public void deleteEquipments(@PathVariable Long equipmentId) {

        equipmentsService.deleteEquipment(equipmentId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{equipmentId}")
    public Equipment getEquipmentsById(@PathVariable Long equipmentId) {

        return equipmentsService.getEquipmentById(equipmentId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{equipmentId}/warehouse")
    public Equipment sentWarehouse(@PathVariable Long equipmentId) {

        return equipmentsService.sendToWarehouse(equipmentId);
    }


    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/{equipmentId}/write-off")
    public ResponseEntity<?> writeOffEquipment(@PathVariable Long equipmentId) {

        Equipment equipment = equipmentsService.writeOffEquipment(equipmentId);
        if (equipment != null)
            return ResponseEntity.ok(equipment);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sum")
    public double getAssetsSum() {

        return equipmentsService.getAssetsSum();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sum-percent")
    public double getAssetsSumPercentForWeek() {

        return equipmentsService.getAssetsSumPercentForWeek();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/write-off-stat")
    public double getWriteOffAssets() {

        return equipmentsService.getWriteOffAssets();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/write-off-percent")
    public double getPercentWriteOffAssetsForMonth() {

        return equipmentsService.getPercentWriteOffAssetsForMonth();

    }

}
