package com.example.tourmanagement.mapper.tour;

import com.example.tourmanagement.dto.EnumTypeDTO;
import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.enums.ReservationStatus;
import com.example.tourmanagement.mapper.clients.ClientMapper;
import com.example.tourmanagement.mapper.tour.TourMapper;
import com.example.tourmanagement.model.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TourMapper.class, ClientMapper.class})
public interface ReservationMapper {
    @Mapping(target = "price", source = "reservation", qualifiedByName = "sumPricesByClientIdAndTourId")
    @Mapping(target = "status", qualifiedByName = "mapReservationStatus")
    ReservationResponse toResponse(Reservation reservation);

    @Mapping(target = "status", qualifiedByName = "mapReservationStatus")
    Iterable<ReservationResponse> toResponse(Iterable<Reservation> all);

    @Named("sumPricesByClientIdAndTourId")
    default Double sumPricesByClientIdAndTourId(Reservation reservation) {

        double price = reservation.getTour().getPrice();

        return reservation.getExcursionPrices() + price;
    }

    @Named("mapReservationStatus")
    default EnumTypeDTO mapReservationStatus(ReservationStatus status) {

        return status.toDTO();
    }

}
