package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.ProviderDto;
import com.example.constructionmanagementspring.model.Provider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProviderMapper extends DtoMapper<Provider, ProviderDto> {
}
