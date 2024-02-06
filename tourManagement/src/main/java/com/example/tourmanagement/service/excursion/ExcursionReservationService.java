package com.example.tourmanagement.service.excursion;

import com.example.tourmanagement.dto.response.ExcursionReservationResponse;
import com.example.tourmanagement.model.entity.ExcursionReservationId;

public interface ExcursionReservationService {

    Iterable<ExcursionReservationResponse> getMyReservations();

    Iterable<ExcursionReservationResponse> getMyReservationsByTour(Long tourId);

    void deleteReservation(ExcursionReservationId id);


}
