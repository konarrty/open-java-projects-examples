package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.Equipment;
import com.example.constructionmanagementspring.model.Material;

import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterial(int page);

    Material getMaterialById(Long id);

    Material createMaterial(Material material);

    Material sendToWarehouse(Long materialId);

    Material putMaterial(Material material, Long materialId);

    void deleteMaterial(Long id);

    Material writeOffMaterial(Long id);

    Material dtoToEntity(Material materialDto);


}
