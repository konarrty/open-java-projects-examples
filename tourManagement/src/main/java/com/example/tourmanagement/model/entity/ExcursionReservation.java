package com.example.tourmanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "excursion_reservations")
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionReservation {

    @EmbeddedId
    private ExcursionReservationId id = new ExcursionReservationId();

    @MapsId("excursionId")
    @ManyToOne
    private Excursion excursion;

    @MapsId("clientId")
    @ManyToOne
    private Client client = new Client();

    @MapsId("tourId")
    @ManyToOne
    private Tour tour = new Tour();

    public ExcursionReservation(ExcursionReservationId id) {
        this.excursion = new Excursion(id.getExcursionId());
        this.client = new Client(id.getClientId());
        this.tour = new Tour(id.getTourId());

    }

    public ExcursionReservation(Long tourId, Long clientId, Long excursionId) {
        this.excursion = new Excursion(excursionId);
        this.client = new Client(clientId);
        this.tour = new Tour(tourId);

    }

}
