package com.example.tourmanagement.model.entity;

import com.example.tourmanagement.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private ReservationStatus status;

    private String message;

    @ManyToOne
    private Reservation reservation;

    public Notification(ReservationStatus status, String message, Reservation reservation) {
        this.status = status;
        this.message = message;
        this.reservation = reservation;
    }
}
