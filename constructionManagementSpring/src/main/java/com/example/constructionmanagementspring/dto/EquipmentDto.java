package com.example.constructionmanagementspring.dto;

import com.example.constructionmanagementspring.enums.EquipmentStatus;
import lombok.Data;

@Data
public class EquipmentDto {
    private Long id;

    private String registrationNumber;

    private EquipmentStatus equipmentStatus;

    private AssetsDto assets;
}
