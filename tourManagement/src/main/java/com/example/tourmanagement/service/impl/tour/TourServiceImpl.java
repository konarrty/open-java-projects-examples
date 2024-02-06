package com.example.tourmanagement.service.impl.tour;

import com.example.tourmanagement.filtration.impl.TourFiltersWithSortRequest;
import com.example.tourmanagement.dto.request.TourRequest;
import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.dto.response.TourDetailsResponse;
import com.example.tourmanagement.dto.response.TourWithDetailsResponse;
import com.example.tourmanagement.exception.LogicException;
import com.example.tourmanagement.mapper.tour.TourDetailsMapper;
import com.example.tourmanagement.mapper.tour.TourMapper;
import com.example.tourmanagement.model.entity.Client;
import com.example.tourmanagement.model.entity.Tour;
import com.example.tourmanagement.repository.tour.TourRepository;
import com.example.tourmanagement.service.tour.ReservationService;
import com.example.tourmanagement.service.tour.TourService;
import com.example.tourmanagement.specification.Specifications;
import com.example.tourmanagement.specification.TourSpecification;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    private final ReservationService reservationService;

    private final TourMapper tourMapper;
    private final TourDetailsMapper detailsMapper;

    @Override
    public Iterable<TourWithDetailsResponse> getAllTours(String sort, Boolean hotTour) {
        var tours = tourRepository.findAll(
                Example.of(new Tour(hotTour)),
                sort == null ? Sort.unsorted() : Sort.by(sort));

        return tourMapper.toResponseWithDetails(tours);
    }

    @Override
    public Iterable<TourDetailsResponse> getAllTours(TourFiltersWithSortRequest request) {
        Specification<Tour> specification = request.getFilters().stream()
                .map(Specifications::addSpecification)
                .reduce(TourSpecification::withNull, Specification::and);

        Sort sort = request.getSort().toSpringSort();

        var tours = tourRepository.findAll(specification, sort).stream()
                .map(Tour::getDetails)
                .collect(Collectors.toSet());

        return detailsMapper.toResponse(tours);
    }


    @Override
    public TourWithDetailsResponse createTour(TourRequest tourRequest) {
        Tour tour = tourMapper.toEntity(tourRequest);
        tourRepository.save(tour);

        return tourMapper.toResponseWithDetails(tour);
    }

    @Override
    public TourWithDetailsResponse patchTours(TourRequest newTour, Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Тур не найден!"));

        tourMapper.partialUpdate(tour, newTour);
        tourRepository.save(tour);

        return tourMapper.toResponseWithDetails(tour);
    }

    @Override
    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }

    @Override
    public Tour getTourById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Тур не найден!"));
    }

    @Override
    public TourWithDetailsResponse findTourById(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Тур не найден!"));

        return tourMapper.toResponseWithDetails(tour);
    }

    @Override
    @Transactional
    public ReservationResponse bookTour(Long id) {
        Tour tour = getTourById(id);

        if (tour.getStartDateTime().isBefore(LocalDateTime.now()))
            throw new LogicException("Тур уже состоялся!");

        tourRepository.decrementCapacity(id);
        Client client = ClientContextUtils.getClient();

        return reservationService.createReservation(client, tour);
    }

    @Transactional
    @Override
    public void updateLastMinuteTourGroup() {
        LocalDateTime dateTime = LocalDateTime.now().plusWeeks(2);

        tourRepository.findAllByLastTimeTourAndStartDateTimeIsLessThanEqual(dateTime);
    }
}
