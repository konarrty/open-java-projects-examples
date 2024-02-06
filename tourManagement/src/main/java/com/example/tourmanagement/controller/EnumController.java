package com.example.tourmanagement.controller;

import com.example.tourmanagement.dto.EnumTypeDTO;
import com.example.tourmanagement.enums.NutritionType;
import com.example.tourmanagement.enums.RestType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@CrossOrigin("*")
@RestController
@RequestMapping("/types")
public class EnumController {

    @GetMapping("/rest-types")
    public Iterable<EnumTypeDTO> getAllRestTypes() {

        return Arrays.stream(RestType.values())
                .map(RestType::toDTO)
                .toList();
    }

    @GetMapping("/nutrition-types")
    public Iterable<EnumTypeDTO> getAllNutritionTypes() {

        return Arrays.stream(NutritionType.values())
                .map(NutritionType::toDTO)
                .toList();
    }
}
