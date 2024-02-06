package com.example.tourmanagement.service.hotel;

import com.example.tourmanagement.dto.HotelReviewDTO;
import com.example.tourmanagement.model.entity.HotelReview;

public interface HotelReviewService {
    Iterable<HotelReviewDTO> getAllHotelReviews(Long hotelId);

    HotelReviewDTO createHotelReview(HotelReviewDTO hotelReviewDTO);

    HotelReviewDTO patchHotelReviews(HotelReviewDTO newHotelReview, Long id);

    void deleteHotelReview(Long id);

    HotelReviewDTO getHotelReviewById(Long id);

    HotelReview findHotelReviewById(Long id);
}
