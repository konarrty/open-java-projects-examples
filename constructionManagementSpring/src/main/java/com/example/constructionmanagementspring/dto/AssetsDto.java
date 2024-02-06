package com.example.constructionmanagementspring.dto;

import com.example.constructionmanagementspring.enums.EquipmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssetsDto {

    private Long id;

    private String name;

    private UnitDto unit;

    private double coast;

    private ProviderDto provider;

//    private EquipmentStatus equipmentStatus;

    private LocalDateTime createDate;
}
