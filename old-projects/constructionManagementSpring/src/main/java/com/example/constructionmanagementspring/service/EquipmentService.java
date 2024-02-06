package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.Equipment;
import com.example.constructionmanagementspring.model.Provider;

import java.util.List;

public interface EquipmentService {
    List<Equipment> getAllEquipments(int page);

    Equipment getEquipmentById(Long id);

    Equipment createEquipment(Equipment equipment);

    Equipment updateQr(String qr, Equipment equipment);

    Equipment putEquipment(Equipment equipment, Long equipmentsId);

    void deleteEquipment(Long id);

    Equipment addProviderToEquipment(Long equipmentId, Provider provider);

    Equipment sendToWarehouse(Long equipmentId);

    double getAssetsSum();

    double getAssetsSumPercentForWeek();

    Equipment writeOffEquipment(Long equipmentId);

    double getPercentWriteOffAssetsForMonth();

    double getWriteOffAssets();
}
