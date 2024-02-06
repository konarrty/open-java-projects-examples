package com.example.testbyport.mapper;

import com.example.testbyport.enums.Month;
import dto.MonthDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MonthMapper {

    MonthDTO toDto(Month task);

    Iterable<MonthDTO> toDto(Iterable<Month> task);


}