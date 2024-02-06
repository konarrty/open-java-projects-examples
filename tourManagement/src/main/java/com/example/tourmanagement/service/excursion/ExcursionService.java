package com.example.tourmanagement.service.excursion;

import com.example.tourmanagement.dto.request.ExcursionRequest;
import com.example.tourmanagement.dto.response.ExcursionReservationResponse;
import com.example.tourmanagement.dto.response.ExcursionResponse;
import com.example.tourmanagement.model.entity.Excursion;

import java.util.List;

public interface ExcursionService {
    Iterable<ExcursionResponse> getAllExcursions(Long detailsId);

    ExcursionResponse createExcursion(ExcursionRequest request);

    ExcursionResponse patchExcursions(ExcursionRequest newExcursion, Long id);

    void deleteExcursion(Long id);

    Excursion getExcursionById(Long id);

    ExcursionReservationResponse bookExcursion(Long excursionId, Long id);

    Iterable<ExcursionReservationResponse> putExcursions(Long tourId, List<Long> excursionIds);
}
