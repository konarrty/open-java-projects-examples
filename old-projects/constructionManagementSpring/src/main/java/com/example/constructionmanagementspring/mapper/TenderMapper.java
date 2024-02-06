package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.TenderDto;
import com.example.constructionmanagementspring.model.Tender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenderMapper extends DtoMapper<Tender, TenderDto> {
}
