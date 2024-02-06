package com.example.tourmanagement.dto.stat;

import java.time.LocalDate;

public interface IReservationByAgentAndMonth {
    Double getVolume();

    LocalDate getMonth();
}
