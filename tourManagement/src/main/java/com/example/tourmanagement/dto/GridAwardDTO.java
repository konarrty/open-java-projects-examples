package com.example.tourmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridAwardDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private double volume;

    private double factor;
}
