package com.example.tourmanagement.service.impl.excursion;

import com.example.tourmanagement.dto.request.ExcursionRequest;
import com.example.tourmanagement.dto.response.ExcursionReservationResponse;
import com.example.tourmanagement.dto.response.ExcursionResponse;
import com.example.tourmanagement.enums.ReservationStatus;
import com.example.tourmanagement.exception.LogicException;
import com.example.tourmanagement.mapper.excursion.ExcursionMapper;
import com.example.tourmanagement.mapper.excursion.ExcursionReservationMapper;
import com.example.tourmanagement.model.entity.Excursion;
import com.example.tourmanagement.model.entity.ExcursionReservation;
import com.example.tourmanagement.model.entity.ExcursionReservationId;
import com.example.tourmanagement.model.entity.Reservation;
import com.example.tourmanagement.repository.excursion.ExcursionRepository;
import com.example.tourmanagement.repository.excursion.ExcursionReservationRepository;
import com.example.tourmanagement.service.excursion.ExcursionService;
import com.example.tourmanagement.service.tour.ReservationService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ExcursionServiceImpl implements ExcursionService {

    private final ExcursionRepository excursionRepository;
    private final ExcursionReservationRepository excursionReservationRepository;
    private final ReservationService reservationService;

    private final ExcursionMapper excursionMapper;
    private final ExcursionReservationMapper reservationMapper;

    @Override
    public Iterable<ExcursionResponse> getAllExcursions(Long detailsId) {
        Iterable<Excursion> excursions = excursionRepository.findAllByDetailsId(detailsId);

        return excursionMapper.toResponse(excursions);
    }

    @Override
    public ExcursionResponse createExcursion(ExcursionRequest excursionRequest) {
        Excursion excursion = excursionMapper.toEntity(excursionRequest);
        excursionRepository.save(excursion);

        return excursionMapper.toResponse(excursion);
    }


    @Override
    public ExcursionResponse patchExcursions(ExcursionRequest newExcursion, Long id) {
        Excursion excursion = excursionRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Тур не найден!"));

        excursionMapper.partialUpdate(excursion, newExcursion);
        excursionRepository.save(excursion);

        return excursionMapper.toResponse(excursion);
    }

    @Override
    public void deleteExcursion(Long id) {
        if (!excursionRepository.existsById(id))
            throw new NoSuchElementException("Тур не найден!");

        excursionRepository.deleteById(id);
    }

    @Override
    public Excursion getExcursionById(Long id) {
        return excursionRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Тур не найден!"));
    }

    @Override
    public ExcursionReservationResponse bookExcursion(Long tourId, Long excursionId) {

        Reservation reservation = reservationService.getByTourIdAndClientId(tourId,
                ClientContextUtils.getClient().getId());

        if (reservation.getTour().getStartDateTime().isBefore(LocalDateTime.now()))
            throw new LogicException("Тур уже состоялся!");

        if (reservation.getStatus().equals(ReservationStatus.PAID))
            throw new LogicException("Тур оплачен. Операция невозможна.");

        if (reservation.getStatus().equals(ReservationStatus.REJECTED))
            throw new LogicException("Заявка отклонена. Операция невозможна.");

        ExcursionReservation excursionReservation = new ExcursionReservation(
                new ExcursionReservationId(excursionId,
                        ClientContextUtils.getClient().getId(),
                        tourId));
        excursionReservationRepository.save(excursionReservation);

        return reservationMapper.toResponse(excursionReservation);

    }

    @Override
    public Iterable<ExcursionReservationResponse> putExcursions(Long tourId, List<Long> excursionIds) {

        var clientId = ClientContextUtils.getClient().getId();
        Iterable<ExcursionReservation> reservations = excursionIds
                .stream()
                .map(excursionId ->
                        new ExcursionReservation(
                                tourId,
                                clientId,
                                excursionId))
                .toList();
        List<ExcursionReservation> excursionReservations =
                excursionReservationRepository.saveAll(reservations);

        return reservationMapper.toResponse(excursionReservations);
    }

}
