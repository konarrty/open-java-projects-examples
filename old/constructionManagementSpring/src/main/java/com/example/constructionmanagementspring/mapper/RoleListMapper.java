package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.RoleDto;
import com.example.constructionmanagementspring.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface RoleListMapper extends DtoMapper<List<Role>, List<RoleDto>> {


}