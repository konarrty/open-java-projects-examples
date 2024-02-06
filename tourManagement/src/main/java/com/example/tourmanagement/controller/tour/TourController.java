package com.example.tourmanagement.controller.tour;

import com.example.tourmanagement.filtration.impl.TourFiltersWithSortRequest;
import com.example.tourmanagement.dto.request.TourRequest;
import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.dto.response.TourDetailsResponse;
import com.example.tourmanagement.dto.response.TourWithDetailsResponse;
import com.example.tourmanagement.dto.stat.ReservationByAgentAndMonthResultDTO;
import com.example.tourmanagement.enums.ReservationStatus;
import com.example.tourmanagement.service.tour.ReservationService;
import com.example.tourmanagement.service.tour.TourService;
import com.example.tourmanagement.utils.ClientContextUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService toursService;
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<?> getAllTours(@RequestParam(required = false, defaultValue = "id") String sort,
                                         @RequestParam(required = false) Boolean hotTours) {
        Iterable<TourWithDetailsResponse> toursList = toursService.getAllTours(sort, hotTours);
        if (toursList.iterator().hasNext())
            return ResponseEntity.ok(toursList);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservationsByTourOperator(@RequestParam(required = false) ReservationStatus status) {
        Iterable<ReservationResponse> toursList = reservationService.getAllReservationsByTourOperator(status);
        if (toursList.iterator().hasNext())
            return ResponseEntity.ok(toursList);
        else
            return ResponseEntity.notFound().build();
    }


    @PostMapping("/filter")
    public ResponseEntity<?> filterTours(@RequestBody TourFiltersWithSortRequest request) {
        Iterable<TourDetailsResponse> toursList = toursService.getAllTours(request);
        if (toursList.iterator().hasNext())
            return ResponseEntity.ok(toursList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TourWithDetailsResponse createTours(@Valid @RequestBody TourRequest tourRequest) {
        return toursService.createTour(tourRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTours(@PathVariable Long id) {

        toursService.deleteTour(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TourWithDetailsResponse getTourById(@PathVariable Long id) {

        return toursService.findTourById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public TourWithDetailsResponse patchTours(@RequestBody TourRequest tourRequest, @PathVariable Long id) {

        return toursService.patchTours(tourRequest, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}/reservations")
    public ReservationResponse bookTour(@PathVariable Long id) {

        return toursService.bookTour(id);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<?> getAllReservations(@PathVariable Long id) {
        Iterable<ReservationResponse> reservationsList = reservationService.getAllReservations(id);
        if (reservationsList.iterator().hasNext())
            return ResponseEntity.ok(reservationsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public Iterable<ReservationResponse> getMyReservations() {

        return reservationService.getMyReservations();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my-stat")
    public ReservationByAgentAndMonthResultDTO generateReportByMySales() {

        return reservationService.generateReportByAgent(ClientContextUtils.getAgent().getId());
    }
}
