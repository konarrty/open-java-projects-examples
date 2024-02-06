package com.example.tourmanagement.mapper.regression;

import com.example.tourmanagement.model.ComputedModel;
import com.example.tourmanagement.dto.stat.RegressionModelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegressionMapper {

    RegressionModelDTO toDTO(ComputedModel model);
}
