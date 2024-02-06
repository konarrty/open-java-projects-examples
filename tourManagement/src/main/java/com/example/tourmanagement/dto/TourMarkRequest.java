package com.example.tourmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourMarkRequest {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private int value;

    private String description;

    private Long detailsId;


}
