package com.example.tourmanagement.mapper.bonus;

import com.example.tourmanagement.dto.GridAwardDTO;
import com.example.tourmanagement.mapper.base.DtoMapper;
import com.example.tourmanagement.model.entity.GridAward;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GridAwardMapper extends DtoMapper<GridAward, GridAwardDTO> {
    GridAwardDTO toDTO(GridAward entity);

    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "bonuses", ignore = true)
    GridAward toEntity(GridAwardDTO dto);

    Iterable<GridAwardDTO> toDTO(Iterable<GridAward> plan);

    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "bonuses", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget GridAward target, GridAwardDTO source);
}
