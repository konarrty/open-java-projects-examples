package com.example.tourmanagement.repository.hotel;

import com.example.tourmanagement.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Iterable<Hotel> findAllByOperatorId(Long operatorId);
}
