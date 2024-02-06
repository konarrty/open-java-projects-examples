package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.AssetsDto;
import com.example.constructionmanagementspring.model.Assets;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetsMapper extends DtoMapper<Assets, AssetsDto> {

}