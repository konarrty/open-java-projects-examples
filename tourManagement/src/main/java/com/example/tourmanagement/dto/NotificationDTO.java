package com.example.tourmanagement.dto;

import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private ReservationStatus status;

    private String message;

    private ReservationResponse reservation;
}
