package com.example.tourmanagement.mapper.excursion;

import com.example.tourmanagement.dto.response.ExcursionReservationResponse;
import com.example.tourmanagement.mapper.tour.TourDetailsMapper;
import com.example.tourmanagement.model.entity.ExcursionReservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TourDetailsMapper.class)
public interface ExcursionReservationMapper {
    ExcursionReservationResponse toResponse(ExcursionReservation entity);

    Iterable<ExcursionReservationResponse> toResponse(Iterable<ExcursionReservation> reservations);

}
