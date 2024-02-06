package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.EquipmentDto;
import com.example.constructionmanagementspring.model.Equipment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipmentMapper extends DtoMapper<Equipment, EquipmentDto> {

}