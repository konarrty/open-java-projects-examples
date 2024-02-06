package com.example.tourmanagement.dto;

import com.example.tourmanagement.enums.ReviewType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReviewDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private ReviewType type;

    private String description;

    private Long hotelId;


}
