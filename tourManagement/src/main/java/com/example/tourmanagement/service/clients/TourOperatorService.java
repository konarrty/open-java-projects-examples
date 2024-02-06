package com.example.tourmanagement.service.clients;

import com.example.tourmanagement.dto.TourOperatorDTO;
import com.example.tourmanagement.dto.registration.OperatorRegistrationDTO;
import com.example.tourmanagement.dto.stat.OperatorSalesReportResponse;
import com.example.tourmanagement.model.entity.TourOperator;

public interface TourOperatorService {
    Iterable<TourOperatorDTO> getAllTourOperators();

    TourOperatorDTO createTourOperator(TourOperatorDTO tourOperator);

    TourOperatorDTO editProfile(TourOperatorDTO newTourOperator);

    void deleteTourOperator(Long id);

    TourOperator getTourOperatorById(Long id);

    OperatorSalesReportResponse generateSalesReportByTop5Tours();

    Double sumSalesByOperator();

    Long countSuccessTripsByOperator();

    Long countHotelsByOperator();

    Double averageMarkByOperator();

    TourOperatorDTO registerOperator(OperatorRegistrationDTO operatorDto);

    TourOperatorDTO getMeOperator();
}
