package com.example.tourmanagement.service.impl.tour;

import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.dto.stat.ReservationByAgentAndMonthResultDTO;
import com.example.tourmanagement.dto.stat.ReservationByAgentDTO;
import com.example.tourmanagement.enums.ReservationStatus;
import com.example.tourmanagement.exception.LogicException;
import com.example.tourmanagement.mapper.tour.ReservationMapper;
import com.example.tourmanagement.model.entity.*;
import com.example.tourmanagement.repository.regression.ReservationRepository;
import com.example.tourmanagement.repository.tour.TourRepository;
import com.example.tourmanagement.service.NotificationService;
import com.example.tourmanagement.service.sse.SseService;
import com.example.tourmanagement.service.tour.ReservationService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;

    private final NotificationService notificationService;
    private final SseService sseService;

    private final ReservationMapper reservationMapper;

    @Override
    public Iterable<ReservationResponse> getAllReservations(Long tourId) {

        Iterable<Reservation> reservations;
        if (tourId == 0)
            reservations = reservationRepository.findAll();
        else
            reservations = reservationRepository.findAllByTourId(tourId);

        return reservationMapper.toResponse(reservations);
    }

    @Override
    public Reservation getByTourIdAndClientId(Long tourId, Long clientId) {

        return reservationRepository.findByTourIdAndClientId(tourId, clientId);
    }

    @Override
    public Iterable<ReservationResponse> getMyReservations() {
        Iterable<Reservation> reservations = reservationRepository.findAllByClientId(
                ClientContextUtils.getClient().getId()
        );

        return reservationMapper.toResponse(reservations);
    }

    @Override
    public Iterable<ReservationResponse> getAllReservationsByTourOperator(ReservationStatus status) {
        var operator = ClientContextUtils.getAgent().getOperator();
        Iterable<Reservation> reservations;

        if (status != null)
            reservations = reservationRepository.findAllByTourDetailsOperatorIdAndStatus(operator.getId(), status);
        else
            reservations = reservationRepository.findAllByTourDetailsOperatorId(operator.getId());

        return reservationMapper.toResponse(reservations);
    }

    @Override
    public Iterable<ReservationByAgentDTO> getAllByAgentIdAndCreatedDateTimeAfter(LocalDateTime dateTime) {

        return reservationRepository.findAllByAgentIdAndCreatedDateTimeAfter(dateTime);
    }

    @Override
    public ReservationByAgentAndMonthResultDTO generateReportByAgent(Long agentId) {

        var dtos = reservationRepository.sumByAgentIdForYear(agentId);
        int size = dtos.size();
        var resultDTO = new ReservationByAgentAndMonthResultDTO(12);
        for (int i = 0; i < size; i++) {
            var res = dtos.get(i);
            if (!res.getDateTime().isBefore(LocalDateTime.now().minusYears(1L)))
                resultDTO.add(res.getVolume(), res.getDateTime().getMonth().getValue() - 1);
        }

        return resultDTO;
    }

    @Transactional
    @Override
    public ReservationResponse createReservation(Client client, Tour tour) {
        Reservation reservation = new Reservation(client, tour, ReservationStatus.PROCESSING);
        reservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(reservation);
    }

    @Override
    public double sumPricesByClientIdAndTourId(Long clientId, Long tourId) {

        return reservationRepository.sumPricesByClientIdAndTourId(clientId, tourId);
    }

    @Override
    @Transactional
    public ReservationResponse changeReservationStatus(ReservationId reservationId, ReservationStatus newStatus) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new LogicException("Брони с таким номером не существует"));
        ReservationStatus oldStatus = reservation.getStatus();

        if (newStatus.ordinal() - 1 != oldStatus.ordinal())
            throw new LogicException("Бронь не может перейти из состояния " + oldStatus + " в " + newStatus);

        if (newStatus.equals(ReservationStatus.APPROVED))
            reservation.setAgent(ClientContextUtils.getAgent());

        reservation.setStatus(newStatus);

        return reservationMapper.toResponse(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse approveReservation(ReservationId reservationId) {

        Reservation reservation = getReservationById(reservationId);

        if (reservation.getTour().getStartDateTime().isBefore(LocalDateTime.now()))
            throw new LogicException("Тур уже состоялся!");

        if (!reservation.getStatus().equals(ReservationStatus.PROCESSING))
            throw new LogicException("На данный момент заявка не может быть одобрена!");

        reservation.setStatus(ReservationStatus.APPROVED);
        reservation.setAgent(ClientContextUtils.getAgent());

        var notification = notificationService.createNotification(
                new Notification(ReservationStatus.APPROVED, "Ваша заявка одобрена!", reservation)
        );
        sseService.sendEvent(reservationId.getClientId(), notification);

        return reservationMapper.toResponse(
                reservation
        );
    }

    @Override
    @Transactional
    public ReservationResponse payReservation(ReservationId reservationId) {

        Reservation reservation = getReservationById(reservationId);

        if (reservation.getTour().getStartDateTime().isBefore(LocalDateTime.now()))
            throw new LogicException("Тур уже состоялся!");

        if (!reservation.getStatus().equals(ReservationStatus.APPROVED))
            throw new LogicException("На данный момент оплата невозможна!");

        reservation.setStatus(ReservationStatus.PAID);

        return reservationMapper.toResponse(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse rejectReservation(ReservationId reservationId) {

        Reservation reservation = getReservationById(reservationId);

        if (reservation.getTour().getStartDateTime().isBefore(LocalDateTime.now()))
            throw new LogicException("Тур уже состоялся!");

        if (!reservation.getStatus().equals(ReservationStatus.PROCESSING))
            throw new LogicException("Заявка уже не может быть отменена!");

        reservation.setStatus(ReservationStatus.REJECTED);
        reservation.setAgent(ClientContextUtils.getAgent());
        tourRepository.incrementCapacity(reservationId.getTourId());

        var notification = notificationService.createNotification(
                new Notification(ReservationStatus.REJECTED, "Ваша заявка отклонена!", reservation)
        );
        sseService.sendEvent(reservationId.getClientId(), notification);

        return reservationMapper.toResponse(reservation);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteReservation(ReservationId id) {
        tourRepository.incrementCapacity(id.getTourId());

        if (!reservationRepository.existsById(id))
            throw new NoSuchElementException("Бронирование не найдено!");

        reservationRepository.delete(new Reservation(id));
    }

    @Override
    public Reservation getReservationById(ReservationId id) {

        return reservationRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Бронирование не найдено!"));
    }

    @Override
    public void createBonusesAccordingTheGrid(LocalDateTime dateTime) {

        reservationRepository.createBonusesAccordingTheGrid(dateTime);
    }

}
