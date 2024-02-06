package com.example.tourmanagement.dto.response;

import com.example.tourmanagement.dto.ClientDTO;
import com.example.tourmanagement.dto.EnumTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private ClientDTO client;

    private TourWithDetailsResponse tour;

    private Double price;

    private EnumTypeDTO status;
}
