package com.example.tourmanagement.repository.hotel;

import com.example.tourmanagement.model.entity.HotelReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelReviewRepository extends JpaRepository<HotelReview, Long> {
    Iterable<HotelReview> findAllByHotelId(Long hotelId);
}
