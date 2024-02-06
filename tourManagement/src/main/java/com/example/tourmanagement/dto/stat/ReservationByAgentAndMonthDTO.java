package com.example.tourmanagement.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationByAgentAndMonthDTO {

    private Double volume;

    private LocalDateTime dateTime;

}
