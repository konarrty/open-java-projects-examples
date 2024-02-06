package com.example.tourmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionRequest {

    private Long id;

    private String name;

    private Integer duration;

    private String description;

    private Double price;

    private Long detailsId;

}
