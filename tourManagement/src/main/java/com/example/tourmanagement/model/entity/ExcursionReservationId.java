package com.example.tourmanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionReservationId implements Serializable {

    private Long excursionId;
    private Long clientId;
    private Long tourId;

}
