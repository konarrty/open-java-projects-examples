package com.example.tourmanagement.controller.clients;

import com.example.tourmanagement.dto.TourOperatorDTO;
import com.example.tourmanagement.dto.registration.OperatorRegistrationDTO;
import com.example.tourmanagement.dto.stat.OperatorSalesReportResponse;
import com.example.tourmanagement.service.clients.TourOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/operators")
@RequiredArgsConstructor
public class TourOperatorController {

    private final TourOperatorService tourOperatorsService;

    @GetMapping
    public ResponseEntity<?> getAllTourOperators() {

        Iterable<TourOperatorDTO> tourOperatorsList = tourOperatorsService.getAllTourOperators();
        if (tourOperatorsList.iterator().hasNext())
            return ResponseEntity.ok(tourOperatorsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TourOperatorDTO createTourOperators(@RequestBody TourOperatorDTO tourOperator) {

        return tourOperatorsService.createTourOperator(tourOperator);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public TourOperatorDTO registerOperator(@RequestBody OperatorRegistrationDTO operatorDto) {

        return tourOperatorsService.registerOperator(operatorDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTourOperators(@PathVariable Long id) {

        tourOperatorsService.deleteTourOperator(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public TourOperatorDTO editProfile(@RequestBody TourOperatorDTO tourOperatorDTO) {

        return tourOperatorsService.editProfile(tourOperatorDTO);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sales-report")
    public OperatorSalesReportResponse generateSalesReportByTop5Tours() {

        return tourOperatorsService.generateSalesReportByTop5Tours();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sales-sum")
    public Double sumSalesByOperator() {

        return tourOperatorsService.sumSalesByOperator();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/amount-trip")
    public Long countSuccessTripsByOperator() {

        return tourOperatorsService.countSuccessTripsByOperator();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/amount-hotels")
    public Long countHotelsByOperator() {

        return tourOperatorsService.countHotelsByOperator();

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/mark")
    public Double averageMarkByOperator() {

        return tourOperatorsService.averageMarkByOperator();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public TourOperatorDTO getMeOperator() {

        return tourOperatorsService.getMeOperator();
    }

}
