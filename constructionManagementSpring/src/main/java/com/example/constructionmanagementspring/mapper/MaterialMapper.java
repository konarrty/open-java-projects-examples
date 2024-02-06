package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.MaterialDto;
import com.example.constructionmanagementspring.model.Material;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper extends DtoMapper<Material, MaterialDto> {

}