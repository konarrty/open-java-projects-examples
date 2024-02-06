package com.example.tourmanagement.dto.response;

import com.example.tourmanagement.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDetailsResponse {

    private Long id;

    private String name;

    private Double rating;

    private String description;

    private double price;

    private String restType;

    private String nutritionType;

    private TourOperatorDTO operator;

    private HotelDTO hotel;

    private List<PlanDTO> plans = new ArrayList<>();

    private List<TourResponse> tours = new ArrayList<>();

    private List<ExcursionResponse> excursions = new ArrayList<>();

    private List<String> images;

}
