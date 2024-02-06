package com.example.tourmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionResponse {

    private Long id;

    private String name;

    private Integer duration;

    private String description;

    private Double price;

//    private TourDetails details;

}
