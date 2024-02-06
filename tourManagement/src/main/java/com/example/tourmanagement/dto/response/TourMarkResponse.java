package com.example.tourmanagement.dto.response;

import com.example.tourmanagement.dto.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourMarkResponse {

    private Long id;

    private int value;

    private String description;

    private TourDetailsResponse details;

    private ClientDTO client;

}
