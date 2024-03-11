package com.example.tourmanagement.controller.tour;

import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.model.entity.ReservationId;
import com.example.tourmanagement.service.tour.ReservationService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@CrossOrigin("*")
@RestController
@RequestMapping("/tours/{tourId}/clients")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationsService;


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{clientId}/approval")
    public ReservationResponse approveReservation(
            @PathVariable Long tourId,
            @PathVariable Long clientId) {

        return reservationsService.approveReservation(
                new ReservationId(clientId, tourId)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/payment")
    public ReservationResponse payReservation(
            @PathVariable Long tourId,
            @RequestParam
            @CreditCardNumber(ignoreNonDigitCharacters = true, message = "Неверный номер карты.") String number) {

        log.info("Карта с номером " + number + " прошла валидацию");

        return reservationsService.payReservation(
                new ReservationId(
                        ClientContextUtils.getClient().getId(),
                        tourId)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{clientId}/cancellation")
    public ReservationResponse rejectReservation(@PathVariable Long tourId, @PathVariable Long clientId) {

        return reservationsService.rejectReservation(
                new ReservationId(clientId, tourId)
        );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{clientId}")
    public void deleteReservations(@PathVariable Long tourId, @PathVariable Long clientId) {

        reservationsService.deleteReservation(
                new ReservationId(clientId, tourId)
        );
    }
}
