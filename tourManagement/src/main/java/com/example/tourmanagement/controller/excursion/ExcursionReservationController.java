package com.example.tourmanagement.controller.excursion;

import com.example.tourmanagement.dto.response.ExcursionReservationResponse;
import com.example.tourmanagement.model.entity.ExcursionReservationId;
import com.example.tourmanagement.service.excursion.ExcursionReservationService;
import com.example.tourmanagement.service.excursion.ExcursionService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/tours/{tourId}/excursions")
@RequiredArgsConstructor
public class ExcursionReservationController {

    private final ExcursionReservationService reservationsService;
    private final ExcursionService excursionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public Iterable<ExcursionReservationResponse> getMyExcursionReservations(@PathVariable Long tourId) {

        return reservationsService.getMyReservationsByTour(tourId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{excursionId}")
    public ExcursionReservationResponse bookExcursion(@PathVariable Long excursionId, @PathVariable Long tourId) {

        return excursionService.bookExcursion(tourId, excursionId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public Iterable<ExcursionReservationResponse> putExcursions(List<Long> excursionIds, @PathVariable Long tourId) {

        return excursionService.putExcursions(tourId, excursionIds);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{excursionId}")
    public void deleteReservations(@PathVariable Long tourId, @PathVariable Long excursionId) {

        reservationsService.deleteReservation(new ExcursionReservationId(excursionId, ClientContextUtils.getClient().getId(), tourId));
    }

}
