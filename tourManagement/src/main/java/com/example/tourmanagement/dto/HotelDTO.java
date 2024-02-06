package com.example.tourmanagement.dto;

import com.example.tourmanagement.dto.response.SpecialityResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;

    private String country;

    private String city;

    private String street;

    private Integer house;

    private Integer stars;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> imageURL;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<HotelReviewDTO> reviews = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<SpecialityResponse> specialities = new ArrayList<>();
}
