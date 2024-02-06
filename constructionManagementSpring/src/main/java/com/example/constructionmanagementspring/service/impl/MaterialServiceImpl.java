package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.enums.EquipmentStatus;
import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.Assets;
import com.example.constructionmanagementspring.model.Material;
import com.example.constructionmanagementspring.repository.MaterialRepository;
import com.example.constructionmanagementspring.service.AssetsService;
import com.example.constructionmanagementspring.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    private final AssetsService assetsService;


    @Override
    public List<Material> getAllMaterial(int page) {

        return materialRepository.findAll(
                        PageRequest.of(page - 1, 5))
                .getContent();
    }

    @Override
    public Material createMaterial(Material material) {

        return materialRepository.save(material);
    }

    @Override
    public Material putMaterial(Material newMaterial, Long materialId) {
        Material material = materialRepository.findById(materialId).orElseThrow(() ->
                new NoSuchEntityException("Товар не найден!"));

        newMaterial.setId(material.getId());

        return materialRepository.save(dtoToEntity(newMaterial));

    }

    @Override
    public Material sendToWarehouse(Long materialId) {

        Material material = materialRepository.getReferenceById(materialId);
        material.getAssets().setEquipmentStatus(EquipmentStatus.IN_STOCK);

        return materialRepository.save(material);

    }

    @Override
    public Material writeOffMaterial(Long id) {

        Material material = getMaterialById(id);
        material.getAssets().setEquipmentStatus(EquipmentStatus.WRITE_OFF);

        return materialRepository.save(material);
    }

    @Override
    public void deleteMaterial(Long id) {
        if (!materialRepository.existsById(id))
            throw new NoSuchEntityException("Товар не найден!");

        materialRepository.deleteById(id);
    }

    @Override
    public Material getMaterialById(Long id) {

        return materialRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Товар не найден!"));
    }


    public Material dtoToEntity(Material materialDto) {

        Assets assets = assetsService.getAssetsById(materialDto.getAssets().getId());

        return Material.builder()
                .id(materialDto.getId())
                .assets(assets)
                .build();
    }
}
