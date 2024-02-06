package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.AccountEquipmentDto;
import com.example.constructionmanagementspring.model.AccountEquipment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountEquipmentMapper extends DtoMapper<AccountEquipment, AccountEquipmentDto> {

}