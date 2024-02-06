package com.example.tourmanagement.mapper.clients;

import com.example.tourmanagement.dto.UserDTO;
import com.example.tourmanagement.model.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "agent", ignore = true)
    User toEntity(UserDTO userDTO);

    Iterable<UserDTO> toDto(Iterable<User> user);

    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "agent", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget User user, UserDTO newUserDTO);

}

