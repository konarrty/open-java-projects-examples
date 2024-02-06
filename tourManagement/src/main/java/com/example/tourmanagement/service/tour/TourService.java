package com.example.tourmanagement.service.tour;

import com.example.tourmanagement.filtration.impl.TourFiltersWithSortRequest;
import com.example.tourmanagement.dto.request.TourRequest;
import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.dto.response.TourDetailsResponse;
import com.example.tourmanagement.dto.response.TourWithDetailsResponse;
import com.example.tourmanagement.model.entity.Tour;

public interface TourService {
    Iterable<TourWithDetailsResponse> getAllTours(String sort, Boolean hotTours);

    Iterable<TourDetailsResponse> getAllTours(TourFiltersWithSortRequest request);

    TourWithDetailsResponse createTour(TourRequest request);

    TourWithDetailsResponse patchTours(TourRequest newTour, Long id);

    void deleteTour(Long id);

    Tour getTourById(Long id);

    TourWithDetailsResponse findTourById(Long id);

    ReservationResponse bookTour(Long id);

    void updateLastMinuteTourGroup();
}
