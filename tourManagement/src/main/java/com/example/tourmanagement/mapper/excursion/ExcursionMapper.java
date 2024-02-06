package com.example.tourmanagement.mapper.excursion;

import com.example.tourmanagement.dto.request.ExcursionRequest;
import com.example.tourmanagement.dto.response.ExcursionResponse;
import com.example.tourmanagement.mapper.tour.TourDetailsMapper;
import com.example.tourmanagement.model.entity.Excursion;
import com.example.tourmanagement.model.entity.TourDetails;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = TourDetailsMapper.class)
public interface ExcursionMapper  {
    ExcursionResponse toResponse(Excursion entity);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "details", source = "detailsId", qualifiedByName = "mappingDetails")
    Excursion toEntity(ExcursionRequest dto);

    Iterable<ExcursionResponse> toResponse(Iterable<Excursion> excursions);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "details", source = "detailsId", qualifiedByName = "mappingDetails")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Excursion excursion, ExcursionRequest source);

    @Named("mappingDetails")
    default TourDetails mappingDetails(Long detailsId) {

        if (detailsId == null)
            return null;

        return new TourDetails(detailsId);
    }
}
