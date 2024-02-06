package com.example.tourmanagement.mapper.tour;

import com.example.tourmanagement.dto.request.TourRequest;
import com.example.tourmanagement.dto.response.TourResponse;
import com.example.tourmanagement.dto.response.TourWithDetailsResponse;
import com.example.tourmanagement.model.entity.Tour;
import com.example.tourmanagement.model.entity.TourDetails;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = TourDetailsMapper.class)
public interface TourMapper {

    @Mapping(target = "detailsId", source = "details.id")
    @Mapping(target = "name", source = "details.name")
    TourResponse toResponse(Tour entity);

    @Mapping(target = "price", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "lastTimeTour", ignore = true)
    @Mapping(source = "detailsId", target = "details.id")
    Tour toEntity(TourRequest dto);

    @Mapping(target = "price", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "lastTimeTour", ignore = true)
    @Mapping(source = "detailsId", target = "details", qualifiedByName = "mapEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Tour target, TourRequest source);

    Iterable<TourResponse> toResponse(Iterable<Tour> tours);

    TourWithDetailsResponse toResponseWithDetails(Tour entity);

    Iterable<TourWithDetailsResponse> toResponseWithDetails(Iterable<Tour> entity);


    @Named("mapEntity")
    default TourDetails mapEntity(Long detailsId) {

        return detailsId != null ? new TourDetails(detailsId) : null;

    }

}
