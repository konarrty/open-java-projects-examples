package com.example.tourmanagement.mapper.bonus;

import com.example.tourmanagement.dto.response.BonusResponse;
import com.example.tourmanagement.model.entity.Bonus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BonusMapper {

    @Mapping(target = "sum", expression = "java(entity.evaluateBonus())")
    BonusResponse toResponse(Bonus entity);


}
