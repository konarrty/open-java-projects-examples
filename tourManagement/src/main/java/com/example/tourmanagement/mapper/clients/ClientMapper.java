package com.example.tourmanagement.mapper.clients;

import com.example.tourmanagement.dto.ClientDTO;
import com.example.tourmanagement.dto.registration.ClientRegistrationDTO;
import com.example.tourmanagement.model.entity.Client;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ClientMapper {

    ClientDTO toDTO(Client entity);

    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passportNumber", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "user.password", source = "password", qualifiedByName = "passwordMapping")
    @Mapping(target = "user.email", source = "email")
    Client toEntity(ClientRegistrationDTO dto);

    @Mapping(target = "reservations", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Client target, ClientDTO source);

    Iterable<ClientDTO> toDTO(Iterable<Client> plan);

    @Named("passwordMapping")
    default String passwordMapping(String password) {

        return new BCryptPasswordEncoder().encode(password);
    }
}
