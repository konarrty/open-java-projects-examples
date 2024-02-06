package com.example.tourmanagement.controller.excursion;

import com.example.tourmanagement.dto.request.ExcursionRequest;
import com.example.tourmanagement.dto.response.ExcursionReservationResponse;
import com.example.tourmanagement.dto.response.ExcursionResponse;
import com.example.tourmanagement.service.excursion.ExcursionReservationService;
import com.example.tourmanagement.service.excursion.ExcursionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/excursions")
@RequiredArgsConstructor
public class ExcursionController {

    private final ExcursionService excursionsService;
    private final ExcursionReservationService reservationService;

    @GetMapping
    public ResponseEntity<?> getAllExcursions(@RequestParam(required = false, defaultValue = "0") Long detailsId) {

        Iterable<ExcursionResponse> excursionsList = excursionsService.getAllExcursions(detailsId);
        if (excursionsList.iterator().hasNext())
            return ResponseEntity.ok(excursionsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ExcursionResponse createExcursion(@RequestBody ExcursionRequest excursionRequest) {
        return excursionsService.createExcursion(excursionRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteExcursion(@PathVariable Long id) {

        excursionsService.deleteExcursion(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ExcursionResponse patchExcursion(@RequestBody ExcursionRequest excursionRequest, @PathVariable Long id) {

        return excursionsService.patchExcursions(excursionRequest, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public Iterable<ExcursionReservationResponse> getMyExcursionReservations() {

        return reservationService.getMyReservations();
    }


}
