package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.RoleDto;
import com.example.constructionmanagementspring.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends DtoMapper<Role, RoleDto> {


}