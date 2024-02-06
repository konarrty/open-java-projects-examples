package com.example.constructionmanagementspring.mapper;

import com.example.constructionmanagementspring.dto.UserDto;
import com.example.constructionmanagementspring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends DtoMapper<User, UserDto>{
}
