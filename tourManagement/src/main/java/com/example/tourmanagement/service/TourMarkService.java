package com.example.tourmanagement.service;

import com.example.tourmanagement.dto.TourMarkRequest;
import com.example.tourmanagement.dto.response.TourMarkResponse;
import com.example.tourmanagement.model.entity.TourMark;

public interface TourMarkService {
    Iterable<TourMarkResponse> getAllMarksByDetails(Long detailsId, Integer size);

    TourMarkResponse createMark(TourMarkRequest markDTO);

    TourMarkResponse patchMarks(TourMarkRequest newMark, Long id);

    void deleteMark(Long id);

    TourMark getMarkById(Long id);

    Double calculateAverageMarkByDetailsId(Long detailsId);

    TourMarkResponse getMyMarkByDetailsId(Long detailsId);
}
