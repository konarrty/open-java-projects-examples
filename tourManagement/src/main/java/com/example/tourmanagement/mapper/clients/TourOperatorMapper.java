package com.example.tourmanagement.mapper.clients;

import com.example.tourmanagement.dto.TourOperatorDTO;
import com.example.tourmanagement.dto.registration.OperatorRegistrationDTO;
import com.example.tourmanagement.model.entity.TourOperator;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TourOperatorMapper {
    TourOperatorDTO toDTO(TourOperator operator);

    TourOperator toEntity(TourOperatorDTO operatorDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "user.password", source = "password", qualifiedByName = "passwordMapping")
    @Mapping(target = "user.email", source = "email")
    TourOperator toEntity(OperatorRegistrationDTO dto);

    Iterable<TourOperatorDTO> toDTO(Iterable<TourOperator> operator);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget TourOperator target, TourOperatorDTO source);

    @Named("passwordMapping")
    default String passwordMapping(String password) {

        return new BCryptPasswordEncoder().encode(password);
    }
}
