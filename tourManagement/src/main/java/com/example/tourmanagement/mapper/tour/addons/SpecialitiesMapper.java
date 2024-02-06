package com.example.tourmanagement.mapper.tour.addons;

import com.example.tourmanagement.dto.request.SpecialityRequest;
import com.example.tourmanagement.dto.response.SpecialityResponse;
import com.example.tourmanagement.mapper.base.DtoMapper;
import com.example.tourmanagement.model.entity.Hotel;
import com.example.tourmanagement.model.entity.Speciality;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialitiesMapper extends DtoMapper<Speciality, SpecialityResponse> {
    SpecialityResponse toDTO(Speciality entity);

    @Mapping(source = "hotelId", target = "hotel", qualifiedByName = "hotelMapping")
    Speciality toEntity(SpecialityRequest dto);

    List<SpecialityResponse> toDTO(List<Speciality> plan);

    List<Speciality> toEntity(List<SpecialityResponse> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Speciality target, Speciality source);

    @Named("hotelMapping")
    default Hotel hotelMapping(Long hotelId) {

        if (hotelId == null)
            return null;

        return new Hotel(hotelId);

    }
}
