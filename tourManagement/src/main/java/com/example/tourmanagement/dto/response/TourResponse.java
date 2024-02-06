package com.example.tourmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourResponse {

    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int capacity;

    private Double price;

    private Long detailsId;

    private String name;

}
