package com.example.tourmanagement.model.entity;

import com.example.tourmanagement.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
@Entity
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @EmbeddedId
    private ReservationId id;

    @MapsId("clientId")
    @ManyToOne
    private Client client;

    @MapsId("tourId")
    @ManyToOne
    private Tour tour;

    @Enumerated(EnumType.ORDINAL)
    private ReservationStatus status;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @ManyToOne
    private Agent agent;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Notification> notifications;

    @Formula("( select coalesce(sum(e.price),0) from excursion_reservations r join excursions e on (e.id = r.excursion_id) where r.client_id = client_id and r.tour_id = tour_id)")
    private Double excursionPrices;

    public Reservation(Client client, Tour tour, ReservationStatus status) {
        this.id = new ReservationId();
        this.tour = tour;
        this.client = client;
        this.status = status;

    }

    public Reservation(Client client, Tour tour) {
        this.id = new ReservationId();
        this.tour = tour;
        this.client = client;
    }

    public Reservation(ReservationId id) {
        this.id = new ReservationId();
        this.tour = new Tour(id.getTourId());
        this.client = new Client(id.getClientId());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Reservation that = (Reservation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }

}
