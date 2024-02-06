package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.PassportDto;
import com.example.constructionmanagementspring.model.Passport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper extends DtoMapper<Passport, PassportDto> {
}
