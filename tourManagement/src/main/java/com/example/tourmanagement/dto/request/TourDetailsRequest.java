package com.example.tourmanagement.dto.request;

import com.example.tourmanagement.dto.*;
import com.example.tourmanagement.enums.NutritionType;
import com.example.tourmanagement.enums.RestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDetailsRequest {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private RestType restType;

    private NutritionType nutritionType;

    private TourOperatorDTO operator;

    private Long hotelId;

}
