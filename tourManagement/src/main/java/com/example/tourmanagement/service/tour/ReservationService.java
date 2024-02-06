package com.example.tourmanagement.service.tour;

import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.dto.stat.ReservationByAgentAndMonthResultDTO;
import com.example.tourmanagement.dto.stat.ReservationByAgentDTO;
import com.example.tourmanagement.enums.ReservationStatus;
import com.example.tourmanagement.model.entity.Client;
import com.example.tourmanagement.model.entity.Reservation;
import com.example.tourmanagement.model.entity.ReservationId;
import com.example.tourmanagement.model.entity.Tour;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

public interface ReservationService {
    Iterable<ReservationResponse> getAllReservations(Long tourId);

    Reservation getByTourIdAndClientId(Long tourId, Long clientId);

    Iterable<ReservationResponse> getAllReservationsByTourOperator(ReservationStatus status);

    ReservationResponse createReservation(Client client, Tour tour);

    double sumPricesByClientIdAndTourId(Long client, Long tour);

    ReservationResponse changeReservationStatus(ReservationId reservationId, ReservationStatus newStatus);

    @Transactional
    ReservationResponse approveReservation(ReservationId reservationId);

    ReservationResponse payReservation(ReservationId reservationId);

    void deleteReservation(ReservationId id);

    Iterable<ReservationByAgentDTO> getAllByAgentIdAndCreatedDateTimeAfter(LocalDateTime dateTime);

    ReservationByAgentAndMonthResultDTO generateReportByAgent(Long agentId);

    Reservation getReservationById(ReservationId id);

    void createBonusesAccordingTheGrid(LocalDateTime dateTime);

    Iterable<ReservationResponse> getMyReservations();

    ReservationResponse rejectReservation(ReservationId reservationId);
}
