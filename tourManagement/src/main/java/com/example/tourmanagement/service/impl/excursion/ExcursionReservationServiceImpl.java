package com.example.tourmanagement.service.impl.excursion;

import com.example.tourmanagement.dto.response.ExcursionReservationResponse;
import com.example.tourmanagement.enums.ReservationStatus;
import com.example.tourmanagement.exception.LogicException;
import com.example.tourmanagement.mapper.excursion.ExcursionReservationMapper;
import com.example.tourmanagement.model.entity.ExcursionReservation;
import com.example.tourmanagement.model.entity.ExcursionReservationId;
import com.example.tourmanagement.model.entity.Reservation;
import com.example.tourmanagement.repository.excursion.ExcursionReservationRepository;
import com.example.tourmanagement.service.excursion.ExcursionReservationService;
import com.example.tourmanagement.service.tour.ReservationService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExcursionReservationServiceImpl implements ExcursionReservationService {

    private final ExcursionReservationRepository reservationRepository;
    private final ExcursionReservationMapper reservationMapper;

    private final ReservationService reservationService;

    @Override
    public Iterable<ExcursionReservationResponse> getMyReservationsByTour(Long tourId) {

        Iterable<ExcursionReservation> reservations = reservationRepository.findAllByClientIdAndTourId(
                ClientContextUtils.getClient().getId(),
                tourId);

        return reservationMapper.toResponse(reservations);
    }

    @Override
    public Iterable<ExcursionReservationResponse> getMyReservations() {

        var clientId = ClientContextUtils.getClient().getId();

        Iterable<ExcursionReservation> reservations = reservationRepository.findAllByClientId(clientId);
        return reservationMapper.toResponse(reservations);
    }

    @Override
    public void deleteReservation(ExcursionReservationId id) {
        Reservation reservation = reservationService.getByTourIdAndClientId(id.getTourId(),
                id.getClientId());

        if (reservation.getTour().getStartDateTime().isBefore(LocalDateTime.now()))
            throw new LogicException("Тур уже состоялся!");

        if (reservation.getStatus().equals(ReservationStatus.PAID))
            throw new LogicException("Тур оплачен. Операция невозможна.");

        if (reservation.getStatus().equals(ReservationStatus.REJECTED))
            throw new LogicException("Заявка отклонена. Операция невозможна.");

        reservationRepository.deleteById(id);
    }

}
