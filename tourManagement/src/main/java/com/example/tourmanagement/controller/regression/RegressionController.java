package com.example.tourmanagement.controller.regression;

import com.example.tourmanagement.dto.stat.RegressionModelDTO;
import com.example.tourmanagement.service.regression.RegressionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/forecast")
@RequiredArgsConstructor
public class RegressionController {

    private final RegressionService regressionService;

    @GetMapping
    public RegressionModelDTO deduceCorrelationOfWeatherAndTouristTraffic(@RequestParam String country) {

        return regressionService.deduceCorrelationOfWeatherAndTouristTraffic(country);
    }
}
