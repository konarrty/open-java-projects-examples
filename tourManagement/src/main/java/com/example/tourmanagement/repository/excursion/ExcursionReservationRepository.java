package com.example.tourmanagement.repository.excursion;

import com.example.tourmanagement.model.entity.ExcursionReservation;
import com.example.tourmanagement.model.entity.ExcursionReservationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcursionReservationRepository extends JpaRepository<ExcursionReservation, ExcursionReservationId> {

    Iterable<ExcursionReservation> findAllByClientIdAndTourId(Long clientId, Long tourId);


    Iterable<ExcursionReservation> findAllByClientId(Long clientId);
}
