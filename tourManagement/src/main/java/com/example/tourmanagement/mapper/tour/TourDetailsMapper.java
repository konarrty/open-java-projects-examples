package com.example.tourmanagement.mapper.tour;

import com.example.tourmanagement.dto.request.TourDetailsRequest;
import com.example.tourmanagement.dto.response.TourDetailsResponse;
import com.example.tourmanagement.enums.NutritionType;
import com.example.tourmanagement.enums.RestType;
import com.example.tourmanagement.mapper.hotel.HotelMapper;
import com.example.tourmanagement.mapper.tour.addons.PlanMapper;
import com.example.tourmanagement.mapper.tour.decorator.TourDetailsDecorator;
import com.example.tourmanagement.model.entity.TourDetails;
import org.mapstruct.*;

import java.util.List;

@DecoratedWith(value = TourDetailsDecorator.class)
@Mapper(componentModel = "spring",
        uses = {PlanMapper.class, HotelMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TourDetailsMapper {

    @Mapping(target = "restType", source = "restType", qualifiedByName = "mapRestType")
    @Mapping(target = "nutritionType", source = "nutritionType", qualifiedByName = "mapNutritionType")
    TourDetailsResponse toResponse(TourDetails entity);

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "marks", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "tours", ignore = true)
    @Mapping(target = "plans", ignore = true)
    @Mapping(target = "excursions", ignore = true)
    @Mapping(target = "hotel.id", source = "hotelId")
    TourDetails toEntity(TourDetailsRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
    void partialUpdate(@MappingTarget TourDetails target, TourDetails source);

    List<TourDetailsResponse> toResponse(Iterable<TourDetails> all);

    @Named("mapRestType")
    default String mapRestType(RestType restType) {

        return restType.getName();
    }

    @Named("mapNutritionType")
    default String mapNutritionType(NutritionType nutritionType) {

        return nutritionType.getName();
    }

}
