package com.example.tourmanagement.dto.response;

import com.example.tourmanagement.dto.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionReservationResponse {

    private ExcursionResponse excursion;

    private ClientDTO client;

    private TourResponse tour;
}
