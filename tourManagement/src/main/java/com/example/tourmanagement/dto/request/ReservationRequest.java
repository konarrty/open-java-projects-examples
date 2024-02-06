package com.example.tourmanagement.dto.request;

import com.example.tourmanagement.dto.ClientDTO;
import com.example.tourmanagement.dto.response.TourWithDetailsResponse;
import com.example.tourmanagement.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private ClientDTO client;

    private TourWithDetailsResponse tour;

    private ReservationStatus status;
}
