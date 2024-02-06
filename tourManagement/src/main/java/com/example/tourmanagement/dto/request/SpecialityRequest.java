package com.example.tourmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityRequest {

    private String name;

    private String description;

    private Long hotelId;
}
