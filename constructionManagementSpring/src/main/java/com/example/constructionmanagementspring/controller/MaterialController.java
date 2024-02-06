package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.MaterialDto;
import com.example.constructionmanagementspring.model.Equipment;
import com.example.constructionmanagementspring.model.Material;
import com.example.constructionmanagementspring.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
@RestController
@RequestMapping("api/materials")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllMaterial(int page) {

        List<Material> materialList = materialService.getAllMaterial(page);

        if (!materialList.isEmpty())
            return ResponseEntity.ok(materialList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/", ""})
    public Material createMaterial(@Valid @BodyToEntity(MaterialDto.class) Material material) {

        return materialService.createMaterial(material);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Material putMaterial(@Valid @RequestBody Material material, @PathVariable Long id) {

        return materialService.putMaterial(material, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteMaterial(@PathVariable Long id) {

        materialService.deleteMaterial(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Material getMaterialById(@PathVariable Long id) {

        return materialService.getMaterialById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{materialId}/warehouse")
    public Material sentWarehouse(@PathVariable Long materialId) {

        return materialService.sendToWarehouse(materialId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{materialId}/write-off")
    public Material writeOffMaterial(@PathVariable Long materialId) {

        return materialService.writeOffMaterial(materialId);
    }


}
