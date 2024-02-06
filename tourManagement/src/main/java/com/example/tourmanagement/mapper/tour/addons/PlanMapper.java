package com.example.tourmanagement.mapper.tour.addons;

import com.example.tourmanagement.dto.PlanDTO;
import com.example.tourmanagement.mapper.base.DtoMapper;
import com.example.tourmanagement.model.entity.Plan;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlanMapper extends DtoMapper<Plan, PlanDTO> {
    @Mapping(target = "detailsId", ignore = true)
    PlanDTO toDTO(Plan entity);

    @Mapping(source = "detailsId", target = "details.id")
    Plan toEntity(PlanDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Plan target, Plan source);

    Iterable<PlanDTO> toDTO(Iterable<Plan> plan);

}
