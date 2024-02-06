package com.example.tourmanagement.mapper.clients;

import com.example.tourmanagement.dto.AgentDTO;
import com.example.tourmanagement.dto.registration.AgentRegistrationDTO;
import com.example.tourmanagement.model.entity.Agent;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = UserMapper.class)
public interface AgentMapper {

    Agent toEntity(AgentDTO agentDTO);

    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "user.password", source = "password", qualifiedByName = "passwordMapping")
    @Mapping(target = "user.email", source = "email")
    Agent toEntity(AgentRegistrationDTO agentDTO);

    AgentDTO toDto(Agent agent);

    Iterable<AgentDTO> toDto(Iterable<Agent> agent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Agent agent, AgentDTO agentDTO);

    @Named("passwordMapping")
    default String passwordMapping(String password) {

        return new BCryptPasswordEncoder().encode(password);
    }

}