package com.example.tourmanagement.repository.tour.addons;

import com.example.tourmanagement.model.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    List<Speciality> findAllByHotelId(Long hotelId);
}
